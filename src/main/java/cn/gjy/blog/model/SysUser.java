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
    private Date lastLoginTime;
    @Alias("last_login_ip")
    private String lastLoginIp;
    @Alias("user_type")
    private Integer userType;
    private String face;
    private Integer level;
    private String sign;

    public static final int USER=1;
    public static final int ADMIN=0;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
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

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
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
