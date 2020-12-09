package cn.gjy.blog.framework.http;

import cn.gjy.blog.framework.annotation.BindParam;
import cn.gjy.blog.framework.annotation.JsonRequestBody;
import cn.gjy.blog.framework.config.FrameworkConfig;
import cn.gjy.blog.framework.log.SimpleLog;
import cn.gjy.blog.framework.model.Model;
import cn.gjy.blog.framework.model.ModelAndView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.*;
import java.util.*;

/**
 * @author gujianyang
 * @date 2020/11/24
 */
public class MethodParamBind {

    private static final SimpleLog log=SimpleLog.log(MethodParamBind.class);

    public static Object[] assignmentMethod(Method method,HttpServletRequest request,HttpServletResponse response) throws Exception{
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] objects=new Object[parameterTypes.length];
        BindParam bindParam;
        Parameter[] parameters = method.getParameters();
        for (int i = 0,l=parameters.length; i < l; i++) {
            if((bindParam=parameters[i].getAnnotation(BindParam.class))!=null){
                if(bindParam.value().isEmpty()&&bindParam.from()==HttpServletRequest.class
                        &&bindParam.header().isEmpty()){
                    //按类型绑定 根据对象属性绑定
                    objects[i]=assignmentParamBean(parameters[i].getType(),request);
                }else if(!bindParam.value().isEmpty()&&bindParam.from()==HttpSession.class){
                    //从session获取对应的key
                    objects[i]=assignmentParamFromSession(bindParam.value(),parameters[i].getType(),request.getSession());
                }else if(!bindParam.value().isEmpty()&&bindParam.from()==HttpServletRequest.class) {
                    //获取对于字段的单个值 只能是基础数据类型 不能是对象
                    objects[i]=bindBasicData(bindParam.value(),parameters[i].getType(),request);
                }else if(bindParam.value().isEmpty()&&!bindParam.header().isEmpty()){
                    objects[i]=changeStringToBasicData(parameters[i].getType(),request.getHeader(bindParam.header()));
                }else
                    throw new RuntimeException("error bind.");
            }else if(parameters[i].getAnnotation(JsonRequestBody.class)!=null){
                objects[i]=jsonSerializeBean(request,parameters[i].getType());
            }
        }
        assignmentDefault(objects,parameterTypes,request,response);
        return objects;
    }

    private static final Gson gson=new Gson();

    private static Object jsonSerializeBean(HttpServletRequest request,Class<?> tClass) throws IOException {
        if(request.getContentType().toLowerCase().contains("application/json")){
            request.setCharacterEncoding("utf-8");
            BufferedReader streamReader = new BufferedReader( new InputStreamReader(request.getInputStream(), "UTF-8"));
            StringBuilder json = new StringBuilder();
            String data;
            while ((data = streamReader.readLine()) != null)
                json.append(data);
            log.v(json.toString());
            TypeToken<?> genericTypeToken = TypeToken.get(tClass);
            return gson.fromJson(json.toString(),genericTypeToken.getType());
        }else
            throw new RuntimeException("content-type错误，必须含有:application/json");
    }

    private static Object bindBasicData(String key, Class<?> type, HttpServletRequest request) {
        String[] v=request.getParameterMap().get(key);
        if(v==null)
            return null;
        if(v.length==0)
            return null;
        else if(v.length==1&&!type.isArray()){
            return changeStringToBasicData(type,v[0]);
        }
        if(type.isArray()){
            Object o=Array.newInstance(type.getComponentType(),v.length);
            for (int i = 0; i < v.length; i++) {
                Array.set(o,i,changeStringToBasicData(type.getComponentType(),v[i]));
            }
            return o;
        }
        return null;
    }

    private static Object changeStringToBasicData(Class<?> t,String v){
        if(v==null)
            return null;
        if(t==String.class)
            return v;
        if(t==Integer.class||t==int.class){
            return Integer.parseInt(v);
        }else if(t==Double.class||t==double.class){
            return Double.parseDouble(v);
        }else if(t==char.class){
            return v.length()>0?v.charAt(0):null;
        }else if(t==Long.class||t==long.class){
            return Long.parseLong(v);
        }else if(t==Short.class||t==short.class){
            return Short.parseShort(v);
        }else if(t==Float.class||t==float.class){
            return Float.parseFloat(v);
        }else if(t==Byte.class||t==byte.class){
            return Byte.parseByte(v);
        }else if(t==Boolean.class||t==boolean.class){
            return "true".equals(v)||"1".equals(v);
        }else
            throw new RuntimeException("error type:"+t.getName());
    }

    private static Object assignmentParamFromSession(String key,Class<?> type, HttpSession session) {
        Object o=session.getAttribute(key);
        if(o!=null&&
                (o.getClass()==type||type.isAssignableFrom(o.getClass()))){
            return o;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static <T> T assignmentParamBean(Class<T> tClass,HttpServletRequest request) throws Exception {
        if(Map.class.isAssignableFrom(tClass)){
            return (T) paramsToMap(request,tClass);
        }else if(tClass.isInterface()){
            throw new RuntimeException("interface can't instance  :"+tClass);
        }else if(!tClass.getName().startsWith(FrameworkConfig.basePackage)){
            throw new RuntimeException("绑定对象仅限于  :"+ FrameworkConfig.basePackage+".*");
        }
        T t=tClass.newInstance();
        Field[] declaredFields = tClass.getDeclaredFields();
        Map<String,Object> paramsMap=paramsToMap(request,HashMap.class);
        Object ob;
        for (int i = 0; i < declaredFields.length; i++) {
            if((ob=paramsMap.get(declaredFields[i].getName()))!=null){
                if(declaredFields[i].getType().isArray()){
                    Object o=Array.newInstance(declaredFields[i].getType().getComponentType(),Array.getLength(ob));
                    for (int j = 0,l=Array.getLength(o); j < l; j++) {
                        Array.set(o,i,changeStringToBasicData(declaredFields[i].getType().getComponentType(),
                                String.valueOf(Array.get(ob,j))));
                    }
                    declaredFields[i].setAccessible(true);
                    declaredFields[i].set(t,ob);
                }else {
                    declaredFields[i].setAccessible(true);
                    declaredFields[i].set(t,changeStringToBasicData(declaredFields[i].getType(),String.valueOf(ob)));
                }
            }
        }
        return t;
    }

    @SuppressWarnings("all")
    private static Map<String,Object> paramsToMap(HttpServletRequest request,Class<?> tClass) throws IllegalAccessException, InstantiationException {
        Map map= (Map) tClass.newInstance();
        Map<String, String[]> parameterMap = request.getParameterMap();
        String[] vs;
        Iterator<String> iterator = parameterMap.keySet().iterator();
        String key;
        while (iterator.hasNext()) {
            key=iterator.next();
            vs=parameterMap.get(key);
            if(vs==null||vs.length==0){
                map.put(key,null);
            }else if(vs.length==1){
                map.put(key,vs[0]);
            }else {
                map.put(key,vs);
            }
        }
        return map;
    }

    //赋值request等默认参数
    private static void assignmentDefault(Object[] objects, Class<?>[] parameterTypes
            ,HttpServletRequest request,HttpServletResponse response) {
        for (int i = 0; i < parameterTypes.length; i++) {
            if (parameterTypes[i]== HttpServletRequest.class) {
                objects[i]=request;
            }else if(parameterTypes[i]== HttpServletResponse.class){
                objects[i]=response;
            }else if(parameterTypes[i]== HttpSession.class){
                objects[i]=request.getSession();
            }else if(parameterTypes[i]== Model.class){
                objects[i]=new Model(request,response);
            }else if(parameterTypes[i]== ModelAndView.class){
                objects[i]=new ModelAndView(request,response);
            }
        }
    }

}
