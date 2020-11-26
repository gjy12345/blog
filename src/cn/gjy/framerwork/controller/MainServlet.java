package cn.gjy.framerwork.controller;

import cn.gjy.framerwork.annotation.Config;
import cn.gjy.framerwork.annotation.Controller;
import cn.gjy.framerwork.annotation.Route;
import cn.gjy.framerwork.annotation.Service;
import cn.gjy.framerwork.config.ControllerConfig;
import cn.gjy.framerwork.handle.ErrorHandle;
import cn.gjy.framerwork.http.MethodParamBind;
import cn.gjy.framerwork.http.UrlMatchEngine;
import cn.gjy.framerwork.http.impl.StringUrlMatchEngine;
import cn.gjy.framerwork.log.SimpleLog;
import cn.gjy.framerwork.model.MatchResult;
import cn.gjy.framerwork.tool.ClassTool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

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

    @Override
    public void init() throws ServletException {
        System.out.println("INTI");
        super.init();
        this.contextPathLength=this.getServletContext().getContextPath().length();
        ClassTool.setServletContext(getServletContext());
        try {
            initThisParams();
        } catch (Exception e) {
            e.printStackTrace();
            if(!ControllerConfig.USE_FILE_STATIC_REGISTER){
                System.err.println("MainServlet启动失败!");
                return;
            }else {
                System.out.println("MainServlet属性注入失败！");
            }
        }
        try {
            if(ControllerConfig.USE_FILE_STATIC_REGISTER){
                initController(ControllerConfig.getControllerClasses());
            }else {
                initController(ClassTool.loadProjectAllClasses());
            }
        } catch (Exception e) {
            e.printStackTrace();
            if(!ControllerConfig.USE_FILE_STATIC_REGISTER){
                try {
                    initController(ControllerConfig.getControllerClasses());
                } catch (Exception exception) {
                    System.err.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        try {
            initServices();
        } catch (Exception e) {
            e.printStackTrace();
            log.e("动态代理service失败.");
        }
    }

    private void initServices() throws Exception{
        ClassTool.loadProjectAllClasses().stream().filter(aClass -> aClass.getAnnotation(Service.class)!=null)
                .forEach(aClass -> {

        });
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
                            System.out.println("注意 类型:" + field.getType().getName() + " 已被注入,但将被覆盖");
                        }
                        field.set(this,aClass.newInstance());
                        System.out.println("注意 类型:" + field.getType().getName() + " 成功注入.");
                    }else {
                        System.out.println("无此类型注入");
                    }
                }
            }
        }
    }

    private void initController(List<Class<?>> classes) throws Exception {
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
                        temp=url+route.value();
                        this.urlMatchEngine.putNewUrlRule(temp.replace("//","/"),route.method(),
                                o,method);
                    }
                }
            }
        }
        System.out.println("加载route成功,耗时:"+(System.currentTimeMillis()-s));
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
        try {
            MatchResult result=urlMatchEngine.matchUrl(url, method);
            if(result!=null){
                Object[] objects = MethodParamBind.assignmentMethod(result.getMethod(), req, resp);
                resp.setContentType("text/html;charset=utf-8");
                resp.setCharacterEncoding("utf-8");
                result.getMethod().invoke(result.getO(),objects);
            }else {
                if(errorHandle!=null){
                    errorHandle.onUrlNotMatch(url,method,req,resp);
                }
            }
        }catch (Exception e) {
            try {
                if(errorHandle!=null){
                    errorHandle.onException(e,url, method,req,resp);
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
