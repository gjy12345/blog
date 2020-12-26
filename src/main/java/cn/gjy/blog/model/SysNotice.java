package cn.gjy.blog.model;

import cn.gjy.blog.framework.annotation.Alias;

/**
 * @Author gujianyang
 * @Date 2020/12/23
 * @Class SysNotice
 */
public class SysNotice {
    private Integer id;
    private String title;
    private String content;//存储markdown
    @Alias("create_time")
    private String createTime;
    @Alias("create_time_l")
    private Long createTimeL;
    @Alias("update_time")
    private String updateTime;
    private Integer show;

    public void setShow(Integer show) {
        this.show = show;
    }

    public Integer getShow() {
        return show;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Long getCreateTimeL() {
        return createTimeL;
    }

    public void setCreateTimeL(Long createTimeL) {
        this.createTimeL = createTimeL;
    }
}
