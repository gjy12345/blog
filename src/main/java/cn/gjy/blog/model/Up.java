package cn.gjy.blog.model;

import cn.gjy.blog.framework.annotation.Alias;

/**
 * @Author gujianyang
 * @Date 2020/12/16
 * @Class Up
 */
public class Up {
    private Integer id;
    @Alias("article_id")
    private Integer articleId;
    @Alias("user_id")
    private Integer userId;
    @Alias("up_time")
    private String upTime;
    @Alias("up_time_l")
    private Long upTimeL;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUpTime() {
        return upTime;
    }

    public void setUpTime(String upTime) {
        this.upTime = upTime;
    }

    public Long getUpTimeL() {
        return upTimeL;
    }

    public void setUpTimeL(Long upTimeL) {
        this.upTimeL = upTimeL;
    }
}
