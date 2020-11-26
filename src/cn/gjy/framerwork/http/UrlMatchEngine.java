package cn.gjy.framerwork.http;

import cn.gjy.framerwork.annotation.Route;
import cn.gjy.framerwork.model.MatchResult;

import java.lang.reflect.Method;

/**
 * @author gujianyang
 * @date 2020/11/25
 * 匹配url
 */
public interface UrlMatchEngine {

    void putNewUrlRule(String url, Route.HttpMethod[] method, Object o, Method m) throws Exception;

    MatchResult matchUrl(String url, Route.HttpMethod method) throws NoSuchMethodException;
}
