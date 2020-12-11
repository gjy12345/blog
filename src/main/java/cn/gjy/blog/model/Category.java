package cn.gjy.blog.model;

import cn.gjy.blog.framework.annotation.Alias;

/**
 * @Author gujianyang
 * @Date 2020/12/11
 * @Class Category
 * 用户博客分类
 */
public class Category {
    private Integer id;
    private String name;
    private Integer lock;
    private String description;
    @Alias("create_time")
    private String createTime;
    @Alias("create_user")
    private Integer createUser;
    @Alias("update_time")
    private String updateTime;
    @Alias("time_stamp")
    private Long timeStamp;
    private Integer blogUseCount;

    public Integer getBlogUseCount() {
        return blogUseCount;
    }

    public void setBlogUseCount(Integer blogUseCount) {
        this.blogUseCount = blogUseCount;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLock() {
        return lock;
    }

    public void setLock(Integer lock) {
        this.lock = lock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

}
