package cn.gjy.framerwork.http.impl;

import cn.gjy.framerwork.annotation.Config;
import cn.gjy.framerwork.annotation.Route;
import cn.gjy.framerwork.http.UrlMatchEngine;
import cn.gjy.framerwork.model.MatchResult;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gujianyang
 * @date 2020/11/25
 */
@Config(UrlMatchEngine.class)
public class StringUrlMatchEngine implements UrlMatchEngine {

    private Map<String,Object> getObjectMap;
    private Map<String,Object> postObjectMap;
    private Map<String, Method> getMethodMap;
    private Map<String, Method> postMethodMap;

    public StringUrlMatchEngine(){
        this.getMethodMap=new HashMap<>();
        this.postMethodMap=new HashMap<>();
        this.getObjectMap=new HashMap<>();
        this.postObjectMap=new HashMap<>();
    }

    @Override
    public void putNewUrlRule(String url, Route.HttpMethod[] methods, Object o, Method m) throws Exception {
        System.out.println(url+" "+methods[0]);
        for (int i = 0; i < methods.length; i++) {
            if(methods[i]== Route.HttpMethod.GET){
                if(getMethodMap.containsKey(url)){
                    throw new RuntimeException("get route 重复");
                }
                getMethodMap.put(url,m);
                getObjectMap.put(url,o);
            }else if(methods[i]== Route.HttpMethod.POST){
                if(postMethodMap.containsKey(url)){
                    throw new RuntimeException("post route 重复");
                }
                postMethodMap.put(url,m);
                postObjectMap.put(url,o);
            }else{
                throw new NoSuchMethodException("没有此http模块实现");
            }
        }
    }

    @Override
    public MatchResult matchUrl(String url, Route.HttpMethod method) throws NoSuchMethodException{
        System.out.println(url+" "+method);
        Map<String,Object> objectMap;
        Map<String,Method> methodMap;
        if(method== Route.HttpMethod.GET){
            objectMap=getObjectMap;
            methodMap=getMethodMap;
        }else if(method== Route.HttpMethod.POST){
            objectMap=postObjectMap;
            methodMap=postMethodMap;
        }else
            throw new NoSuchMethodException("没有此http模块实现");
        if(methodMap.containsKey(url)){
            return new MatchResult(objectMap.get(url),methodMap.get(url));
        }
        return null;
    }

}
