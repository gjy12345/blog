package cn.gjy.blog.model;

/**
 * @Author gujianyang
 * @Date 2020/12/14
 * @Class DetailedArticle
 * 博客详细的信息
 */
public class DetailedArticle extends Article{
    private String typeName;
    private String pubLevelName;
    private Integer common;//评论数
    private Integer up;//点赞数
    private String userName;
    private String face;//头像
    private String loginUsername;

    public String getLoginUsername() {
        return loginUsername;
    }

    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getPubLevelName() {
        return pubLevelName;
    }

    public void setPubLevelName(String pubLevelName) {
        this.pubLevelName = pubLevelName;
    }

    public Integer getCommon() {
        return common;
    }

    public void setCommon(Integer common) {
        this.common = common;
    }

    public Integer getUp() {
        return up;
    }

    public void setUp(Integer up) {
        this.up = up;
    }

    @Override
    public String toString() {
        return "DetailedArticle{" +
                "typeName='" + typeName + '\'' +
                ", pubLevelName='" + pubLevelName + '\'' +
                ", common=" + common +
                ", up=" + up +
                ", id=" + id +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", comment=" + comment +
                ", description='" + description + '\'' +
                ", keywords='" + keywords + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", thumb='" + thumb + '\'' +
                ", title='" + title + '\'' +
                ", topPriority=" + topPriority +
                ", url='" + url + '\'' +
                ", type=" + type +
                ", content='" + content + '\'' +
                ", publicityLevel=" + publicityLevel +
                ", userId=" + userId +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
