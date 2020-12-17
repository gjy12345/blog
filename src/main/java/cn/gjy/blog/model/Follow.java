package cn.gjy.blog.model;

import cn.gjy.blog.framework.annotation.Alias;

/**
 * @Author gujianyang
 * @Date 2020/12/17
 * @Class Follow
 * 关注
 */
public class Follow {
    private Integer id;
    @Alias("user_id")
    private Integer userId;
    @Alias("follow_id")
    private Integer followId;
    @Alias("follow_time")
    private String followTime;
    @Alias("follow_time_l")
    private Long followTimeL;
    private String userName;
    private String followUserName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFollowId() {
        return followId;
    }

    public void setFollowId(Integer followId) {
        this.followId = followId;
    }

    public String getFollowTime() {
        return followTime;
    }

    public void setFollowTime(String followTime) {
        this.followTime = followTime;
    }

    public Long getFollowTimeL() {
        return followTimeL;
    }

    public void setFollowTimeL(Long followTimeL) {
        this.followTimeL = followTimeL;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFollowUserName() {
        return followUserName;
    }

    public void setFollowUserName(String followUserName) {
        this.followUserName = followUserName;
    }
}
