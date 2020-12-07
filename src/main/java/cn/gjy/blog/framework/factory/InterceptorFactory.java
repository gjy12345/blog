package cn.gjy.blog.framework.factory;

import cn.gjy.blog.framework.http.Interceptor;

import java.util.List;

public interface InterceptorFactory {

    void init(List<Object> objects);

    List<Interceptor> getHandles(String url);
}
