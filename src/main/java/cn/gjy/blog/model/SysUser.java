package cn.gjy.blog.model;

import cn.gjy.blog.framework.annotation.Alias;

import java.util.Date;

/**
 * @Author gujianyang
 * @Date 2020/12/7
 * @Class SysUser
 * 用户实体
 */
public class SysUser {
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private Integer lock;
    @Alias("last_login_time")
    private String lastLoginTime;
    @Alias("last_login_ip")
    private String lastLoginIp;
    @Alias("user_type")
    private Integer userType;
    private String face;
    private String sign;
    @Alias("create_time")
    private String createTime;
    private Integer blogCount;
    private Integer sex;

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getBlogCount() {
        return blogCount;
    }

    public void setBlogCount(Integer blogCount) {
        this.blogCount = blogCount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public static final int USER=1;
    public static final int ADMIN=0;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }


    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getLock() {
        return lock;
    }

    public void setLock(Integer lock) {
        this.lock = lock;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public static class SysUserType{
        private SysUserType(){

        }

        public static final int SYS_ADMIN=0;
        public static final int SYS_USER=1;
        public static final int SYS_GUEST=2;
    }
    
}
