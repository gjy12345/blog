package cn.gjy.blog.framework.factory.impl;

import cn.gjy.blog.framework.annotation.Config;
import cn.gjy.blog.framework.factory.InterceptorFactory;
import cn.gjy.blog.framework.http.Interceptor;
import cn.gjy.blog.framework.log.SimpleLog;

import java.util.*;

/**
 * @author gujianyang
 * @date 2020/11/30
 */
@Config(InterceptorFactory.class)
public class InterceptorFactoryImpl implements InterceptorFactory{

    private static final SimpleLog log=SimpleLog.log(InterceptorFactoryImpl.class);

    private Map<String,Interceptor> interceptorMap;
    private List<String> pattens;

    @Override
    public void init(List<Object> objects) {
        this.interceptorMap=new HashMap<>();
        this.pattens=new ArrayList<>();
        Interceptor interceptor;
        for (int i = 0; i < objects.size(); i++) {
            interceptor= (Interceptor) objects.get(i);
            if(interceptor.registerPatten()==null){
                log.e("拦截器规则为空!");
                throw new NullPointerException("拦截器规则为空!");
            }
            this.pattens.add(interceptor.registerPatten());
            this.interceptorMap.put(interceptor.registerPatten(),interceptor);
        }
        log.v("拦截器规则数:"+pattens.size());
    }

    @Override
    public List<Interceptor> getHandles(String url) {
        List<Interceptor> matches=new ArrayList<>();
        pattens.parallelStream().filter(url::matches).forEach(s -> {
            matches.add(interceptorMap.get(s));
        });
        log.v(url+"===>匹配规则:"+matches.size());
        return matches;
    }
}
