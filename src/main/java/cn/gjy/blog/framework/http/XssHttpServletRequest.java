package cn.gjy.blog.framework.http;

import cn.gjy.blog.framework.tool.XssTool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author gujianyang
 * @date 2020/12/4
 */
public class XssHttpServletRequest extends HttpServletRequestWrapper {

    private Map<String,String[]> filterParams=null;

    public XssHttpServletRequest(HttpServletRequest request) {
        super(request);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        if(filterParams==null){
            filterParams=new HashMap<>();
            Map<String,String[]> params=super.getParameterMap();
            Iterator<String> iterator = params.keySet().iterator();
            String key;
            String[] values;
            while (iterator.hasNext()) {
                key=iterator.next();
                values=params.get(key);
                if(values!=null){
                    for (int i = 0; i < values.length; i++) {
                        //把 <>替换成html转义
                        values[i]= XssTool.encode(values[i]);
                    }
                }
                filterParams.put(key,values);
            }
        }
        return filterParams;
    }
}
