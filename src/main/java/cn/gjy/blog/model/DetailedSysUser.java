package cn.gjy.blog.model;

/**
 * @Author gujianyang
 * @Date 2020/12/21
 * @Class DetailedSysUser
 */
public class DetailedSysUser extends SysUser{
    private String sexName;
    private boolean online;
    private String lastRelease;

    public String getSexName() {
        return sexName;
    }

    public void setSexName(String sexName) {
        this.sexName = sexName;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getLastRelease() {
        return lastRelease;
    }

    public void setLastRelease(String lastRelease) {
        this.lastRelease = lastRelease;
    }
}
