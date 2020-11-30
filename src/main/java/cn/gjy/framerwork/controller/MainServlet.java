package cn.gjy.framerwork.controller;

import cn.gjy.framerwork.annotation.Config;
import cn.gjy.framerwork.annotation.Controller;
import cn.gjy.framerwork.annotation.ResponseBody;
import cn.gjy.framerwork.annotation.Route;
import cn.gjy.framerwork.config.CharsetConfig;
import cn.gjy.framerwork.config.ControllerConfig;
import cn.gjy.framerwork.factory.DataHandleFactory;
import cn.gjy.framerwork.handle.DataHandle;
import cn.gjy.framerwork.handle.ErrorHandle;
import cn.gjy.framerwork.handle.SerializeHandle;
import cn.gjy.framerwork.url.MethodParamBind;
import cn.gjy.framerwork.url.UrlMatchEngine;
import cn.gjy.framerwork.factory.impl.ObjectFactory;
import cn.gjy.framerwork.log.SimpleLog;
import cn.gjy.framerwork.model.MatchResult;
import cn.gjy.framerwork.tool.ClassTool;
import cn.gjy.framerwork.url.impl.StringUrlMatchEngine;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author gujianyang
 * @date 2020/11/25
 * 转发请求
 * 使用反射注入各字段
 */
public class MainServlet extends HttpServlet {

    private int contextPathLength;

    private UrlMatchEngine urlMatchEngine;

    private ErrorHandle errorHandle;

    private static SimpleLog log=SimpleLog.log(MainServlet.class);

    private ObjectFactory objectFactory;

    private SerializeHandle serializeHandle;

    private CharsetConfig charsetConfig;

    private DataHandleFactory dataHandleFactory;

    @Override
    public void init() throws ServletException {
        log.v("INTI start");
        super.init();
        this.contextPathLength=this.getServletContext().getContextPath().length();
        ClassTool.setServletContext(getServletContext());
        try {
            this.objectFactory=new ObjectFactory();
            log.v("init param");
            initThisParams();
        } catch (Exception e) {
            e.printStackTrace();
            if(!ControllerConfig.USE_FILE_STATIC_REGISTER){
                log.e("MainServlet启动失败!");
                return;
            }else {
                log.e("MainServlet属性注入失败！");
            }
        }
        log.v("init controller");
        List<Object> controllerObjects=null;
        try {
            if(ControllerConfig.USE_FILE_STATIC_REGISTER){
                controllerObjects=initController(ControllerConfig.getControllerClasses());
            }else {
                controllerObjects=initController(ClassTool.loadProjectAllClasses());
            }
        } catch (Exception e) {
            e.printStackTrace();
            if(!ControllerConfig.USE_FILE_STATIC_REGISTER){
                try {
                    controllerObjects=initController(ControllerConfig.getControllerClasses());
                } catch (Exception exception) {
                    log.e(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        log.v("init factory");
        try {
            this.objectFactory.setControllerList(controllerObjects);
            this.objectFactory.init();
            log.v("init dataHandle");
            List<Object> dataHandleObject=new ArrayList<>();
            ClassTool.loadProjectAllClasses().stream()
                    .filter(aClass -> {
                        Config config=aClass.getAnnotation(Config.class);
                        return config!=null&&config.value()==DataHandle.class&&
                                DataHandle.class.isAssignableFrom(aClass);
                    }).collect(Collectors.toList()).forEach(aClass -> {
                        try {
                            dataHandleObject.add(objectFactory.getObjectByClass(aClass));
                        } catch (Exception e) {
                            log.e("加载handle失败:"+e.getMessage());
                        }
                    });
            this.dataHandleFactory.init(dataHandleObject);
        } catch (Exception e) {
            e.printStackTrace();
            log.e("创建装配工厂失败:"+e.getMessage());
        }
    }



    //初始化
    private void initThisParams() throws Exception {
        List<Class<?>> classes=ClassTool.loadProjectAllClasses();
        Config config;
        Field[] fields = this.getClass().getDeclaredFields();
        Map<String,Field> fieldMap=new HashMap<>();
        for (int i = 0; i < fields.length; i++) {
            fieldMap.put(fields[i].getType().getName(),fields[i]);
        }
        Field field;
        for (Class<?> aClass : classes) {
            if((config=aClass.getAnnotation(Config.class))!=null){
                if ((field=fieldMap.get(config.value().getName()))!=null) {
                    if(field.getType().isAssignableFrom(aClass)||field.getType()==aClass){
                        if(field.get(this)!=null){
                            log.e("注意 类型:" + field.getType().getName() + " 已被注入,但将被覆盖");
                        }
                        field.setAccessible(true);
                        field.set(this,objectFactory.getObjectByClass(aClass));
                        log.v("注意 类型:" + field.getType().getName() + " 成功注入.");
                    }else {
                        log.v("无此类型注入");
                    }
                }
            }
        }
    }

    private List<Object> initController(List<Class<?>> classes) throws Exception {
        List<Object> controllerList=new ArrayList<>();
        long s=System.currentTimeMillis();
        if(classes==null){
            throw new RuntimeException("加载Controller失败");
        }
        if(this.urlMatchEngine==null)
            this.urlMatchEngine=new StringUrlMatchEngine();
        Class<?> c;
        Route route;
        String url,temp;
        Method[] methods;
        Object o;
        for (int i = 0; i < classes.size(); i++) {
            c=classes.get(i);
            if(c.isInterface())
                continue;
            if(c.getAnnotation(Controller.class)!=null){
                o=c.newInstance();
                controllerList.add(o);
                route=c.getAnnotation(Route.class);
                if(route!=null){
                    url=route.value();
                    if(!url.startsWith("/"))
                        url="/"+url;
                }else {
                    url="";
                }
                methods=c.getMethods();
                for (Method method : methods) {
                    if((route=method.getAnnotation(Route.class))!=null){
                        temp=url+(route.value().startsWith("/")?"":"/")+route.value();
                        this.urlMatchEngine.putNewUrlRule(temp.replace("//","/"),route.method(),
                                o,method);
                    }
                }
            }
        }
        log.v("加载route成功,耗时:"+(System.currentTimeMillis()-s));
        return controllerList;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        execute(req,resp, Route.HttpMethod.GET);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        execute(req,resp, Route.HttpMethod.POST);
    }

    private void execute(HttpServletRequest req, HttpServletResponse resp, Route.HttpMethod method){
        String url=getUrl(req);
        MatchResult result=null;
        try {
            result=urlMatchEngine.matchUrl(url, method);
            if(result!=null){
                if (charsetConfig!=null) {
                    //处理编码
                    charsetConfig.handle(req,resp);
                }
                Object[] objects = MethodParamBind.assignmentMethod(result.getMethod(), req, resp);
                resp.setContentType("text/html;charset=utf-8");
                resp.setCharacterEncoding("utf-8");
                log.v(url+" "+method);
                Object returnData=result.getMethod().invoke(result.getO(),objects);
                Class<?> returnType = result.getMethod().getReturnType();
                if(returnType==Void.class||returnType==void.class){
                    //nothing
                } else if(result.getMethod().getAnnotation(ResponseBody.class)!=null){
                    if(serializeHandle !=null)
                        serializeHandle.handle(req,resp,result.getMethod(),returnData);
                    else
                        throw new RuntimeException("没有找到ResponseBody的序列化处理器");
                }else {
                    dataHandleFactory.handle(req,resp, result.getMethod(),returnData);
                }
            }else {
                log.e(url+" "+method+" not found");
                if(errorHandle!=null){
                    errorHandle.onUrlNotMatch(url,method,req,resp);
                }
            }
        }catch (Exception e) {
            try {
                if(errorHandle!=null){
                    errorHandle.onException(e,url, method,req,resp, result==null?null:result.getMethod());
                }
            }catch (Exception ee){
                ee.printStackTrace();
            }

        }
    }

    private String getUrl(HttpServletRequest req){
        String uri = req.getRequestURI();
        if(contextPathLength>0)
            uri=req.getRequestURI().substring(contextPathLength,uri.length());
        return uri;
    }
}
