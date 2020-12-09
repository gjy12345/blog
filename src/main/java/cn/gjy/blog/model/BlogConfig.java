package cn.gjy.blog.model;

import cn.gjy.blog.framework.annotation.Component;

/**
 * @Author gujianyang
 * @Date 2020/12/7
 * @Class BlogConfig
 */

@Component
public class BlogConfig {

    private Integer year=2020;
    private String blogName="Simple Blogs";

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getBlogName() {
        return blogName;
    }

    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }
}
