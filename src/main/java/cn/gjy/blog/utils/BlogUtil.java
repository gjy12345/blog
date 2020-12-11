package cn.gjy.blog.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Iterator;
import java.util.function.Consumer;

/**
 * @Author gujianyang
 * @Date 2020/12/9
 * @Class BlogUtil
 */
public class BlogUtil {

    private static final String[] unsafeString={"on"};

    //防止xss注入 使用jsoup
    public static String replaceUnsafeString(String html) throws Exception{
        Document document= Jsoup.parse(html);
        Elements children = document.getAllElements();
        int size = children.size();
        Element element;
        for (int i = 0; i < size; i++) {
            element=children.get(i);
            if (element.nodeName().equalsIgnoreCase("script")) {
                //
                element.remove();
            }else if(element.nodeName().equalsIgnoreCase("link")){
                //远程调用js
                element.remove();
            } else {
                Attributes attributes = element.attributes();
                Iterator<Attribute> iterator = attributes.iterator();
                Attribute attribute;
                while (iterator.hasNext()) {
                    attribute=iterator.next();
                    for (String s : unsafeString) {
                        if(attribute.getKey().toLowerCase().contains(s)){
                            attributes.remove(attribute.getKey());
                        }
                    }
                }
            }
        }
        return document.body().toString();
    }
}
