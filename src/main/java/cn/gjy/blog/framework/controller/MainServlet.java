package cn.gjy.blog.framework.controller;

import cn.gjy.blog.framework.annotation.*;
import cn.gjy.blog.framework.config.CharsetConfig;
import cn.gjy.blog.framework.config.ControllerConfig;
import cn.gjy.blog.framework.config.FrameworkConfig;
import cn.gjy.blog.framework.database.datasource.QueueDataSource;
import cn.gjy.blog.framework.database.datasource.impl.QueueDataSourceImpl;
import cn.gjy.blog.framework.factory.DataHandleFactory;
import cn.gjy.blog.framework.factory.InterceptorFactory;
import cn.gjy.blog.framework.handle.DataHandle;
import cn.gjy.blog.framework.handle.ErrorHandle;
import cn.gjy.blog.framework.handle.SerializeHandle;
import cn.gjy.blog.framework.http.Interceptor;
import cn.gjy.blog.framework.http.MethodParamBind;
import cn.gjy.blog.framework.http.UrlMatchEngine;
import cn.gjy.blog.framework.factory.impl.ObjectFactory;
import cn.gjy.blog.framework.http.XssHttpServletRequest;
import cn.gjy.blog.framework.log.SimpleLog;
import cn.gjy.blog.framework.model.MatchResult;
import cn.gjy.blog.framework.tool.ClassTool;
import cn.gjy.blog.framework.http.impl.StringUrlMatchEngine;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author gujianyang
 * @date 2020/11/25
 * 转发请求
 * 使用反射注入各字段
 */
public class MainServlet extends HttpServlet {

    private final Map<Class<?>,Integer> paramsClassLevelMap=new HashMap<>();

    private int contextPathLength;

    private UrlMatchEngine urlMatchEngine;

    private ErrorHandle errorHandle;

    private static final SimpleLog log=SimpleLog.log(MainServlet.class);

    private ObjectFactory objectFactory;

    private SerializeHandle serializeHandle;

    private CharsetConfig charsetConfig;

    private DataHandleFactory dataHandleFactory;

    private InterceptorFactory interceptorFactory;

    private QueueDataSource queueDataSource;

    @Override
    public void init() throws ServletException {
        log.v("INTI start");
        super.init();
        this.contextPathLength=this.getServletContext().getContextPath().length();
        FrameworkConfig.contentPath=getServletContext().getContextPath();
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
            List<Object> dataHandleObjects=new ArrayList<>();
            List<Object> interceptorObjects=new ArrayList<>();
            ClassTool.loadProjectAllClasses().stream()
                    .filter(aClass -> {
                        Config config=aClass.getAnnotation(Config.class);
                        if(config==null)
                            return false;
                        if(config.value()==DataHandle.class&&
                                DataHandle.class.isAssignableFrom(aClass)){
                            return true;
                        }else if(Interceptor.class.isAssignableFrom(aClass)&&config.value()==Interceptor.class){
                            return true;
                        }
                        return false;
                    }).collect(Collectors.toList()).forEach(aClass -> {
                        try {
                            if(DataHandle.class.isAssignableFrom(aClass))
                                dataHandleObjects.add(objectFactory.getObjectByClass(aClass));
                            else if(Interceptor.class.isAssignableFrom(aClass)){
                                interceptorObjects.add(objectFactory.getObjectByClass(aClass));
                            }
                        } catch (Exception e) {
                            log.e("加载handle失败:"+e.getMessage());
                        }
                    });
            log.v("init dataHandle");
            this.dataHandleFactory.init(dataHandleObjects);
            log.v("init interceptor");
            this.interceptorFactory.init(interceptorObjects);
            this.objectFactory.initObjectParams();
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
        Map<Class<?>,Field> fieldMap=new HashMap<>();
        for (int i = 0; i < fields.length; i++) {
            fieldMap.put(fields[i].getType(),fields[i]);
        }
        Field field;
        for (Class<?> aClass : classes) {
            if((config=aClass.getAnnotation(Config.class))!=null){
                if ((field=fieldMap.get(config.value()))!=null) {
                    if(field.getType().isAssignableFrom(aClass)||field.getType()==aClass){
                        if(field.get(this)!=null){
                            if(config.level()>paramsClassLevelMap.get(config.value()))
                                log.e("注意 类型:" + field.getType().getName() + " 已被注入,但将被覆盖");
                            else
                                continue;
                        }
                        field.setAccessible(true);
                        field.set(this,objectFactory.getObjectByClass(aClass));
                        objectFactory.putObject(config.value(),objectFactory.getObjectByClass(aClass));
                        log.v("注意 类型:" + field.getType().getName() + " 成功注入.");
                        paramsClassLevelMap.put(config.value(),config.level());
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

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        errorHandle.onNoSuchMethod(getUrl(req),req,resp,"delete");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        errorHandle.onNoSuchMethod(getUrl(req),req,resp,"put");
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        errorHandle.onNoSuchMethod(getUrl(req),req,resp,"head");
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        errorHandle.onNoSuchMethod(getUrl(req),req,resp,"options");
    }

    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        errorHandle.onNoSuchMethod(getUrl(req),req,resp,"trace");
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
                List<Interceptor> handles = interceptorFactory.getHandles(url);
                if(!interceptorUrl(handles,req,resp, result.getMethod(), result.getO())){
                    return;
                }
                if(FrameworkConfig.xssFilter){
                    //对参数进行xss转义
                    req=new XssHttpServletRequest(req);
                }
                HttpRequestUtil.setRequest(req);
                Object[] objects = MethodParamBind.assignmentMethod(result.getMethod(), req, resp);
                log.v(url+" "+method);
                crossDomain(result,resp);
                Object returnData=result.getMethod().invoke(result.getO(),objects);
                afterInterceptor(handles,req,resp,result,objects,returnData);
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
            log.v(e.getMessage()+" url:"+url);
            try {
                if(errorHandle!=null){
                    errorHandle.onException(e,url, method,req,resp, result==null?null:result.getMethod());
                }
            }catch (Exception ee){
                ee.printStackTrace();
            }

        } finally {
            HttpRequestUtil.removeRequest();
        }
    }

    private void afterInterceptor(List<Interceptor> handles, HttpServletRequest req, HttpServletResponse resp,
                                  MatchResult result,Object[] objects,Object returnData) throws Exception {
        for (Interceptor handle : handles) {
            handle.afterCompletion(req,resp,result.getMethod(),objects,returnData);
        }
    }

    private Map<Method,Boolean> needCrossHeader=new ConcurrentHashMap<>();

    private void crossDomain(MatchResult result, HttpServletResponse resp) {
        Boolean r;
        if ((r=needCrossHeader.get(result.getMethod()))==null) {
            r=(result.getMethod().getAnnotation(CrossDomain.class)!=null)||
                    (result.getO().getClass().getAnnotation(CrossDomain.class)!=null);
            needCrossHeader.put(result.getMethod(),r);
        }
        if(r){
            resp.setHeader("Access-Control-Allow-Origin","*");
        }
    }

    //拦截器处理
    private boolean interceptorUrl(List<Interceptor> handles,HttpServletRequest request,HttpServletResponse response,Method method,
                                   Object methodObject) throws Exception {
        for (Interceptor handle : handles) {
            if(!handle.preHandle(request,response,method,methodObject))
                return false;
        }
        return true;
    }

    private String getUrl(HttpServletRequest req){
        String uri = req.getRequestURI();
        if(contextPathLength>0)
            uri=req.getRequestURI().substring(contextPathLength,uri.length());
        return uri;
    }

    @Override
    public void destroy() {
        super.destroy();
        if(this.queueDataSource!=null){
            log.v("关闭所有数据库连接");
            this.queueDataSource.close();
            this.queueDataSource.deregisterDriver();
            ClassTool.setServletContext(null);
        }
    }
}
