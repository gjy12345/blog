package cn.gjy.blog.model;

import cn.gjy.blog.framework.annotation.Alias;

/**
 * @Author gujianyang
 * @Date 2020/12/10
 * @Class Article
 * 文章表
 */
public class Article {
    protected Integer id;//唯一编号
    @Alias("create_time")
    protected String createTime;//创建时间
    @Alias("update_time")
    protected String updateTime;//更新时间
    @Alias("comment")
    protected Integer comment;//是否可以评论
    protected String description;//描述 展示在未点进去的时候
    protected String keywords;//关键字
    protected String password;//文章密码
    protected Integer status;//状态
    protected String thumb;//封面
    protected String title;//标题
    @Alias("top_priority")
    protected Integer topPriority;//权重
    protected String url;//url编号
    protected Integer type;//分类id
    protected String content;//正文
    @Alias("publicity_level")
    protected Integer publicityLevel;//公开级别
    @Alias("user_id")
    protected Integer userId;
    @Alias("time_stamp")
    protected Long timeStamp;
    protected String markdown;
    private int visit;//访问量

    public int getVisit() {
        return visit;
    }

    public void setVisit(int visit) {
        this.visit = visit;
    }

    public String getMarkdown() {
        return markdown;
    }

    public void setMarkdown(String markdown) {
        this.markdown = markdown;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPublicityLevel() {
        return publicityLevel;
    }

    public void setPublicityLevel(Integer publicityLevel) {
        this.publicityLevel = publicityLevel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTopPriority() {
        return topPriority;
    }

    public void setTopPriority(Integer topPriority) {
        this.topPriority = topPriority;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static final class ArticleState{
        public static final int RELEASE=1;
        public static final int LOCK=3;//锁定
        public static final int DELETE=4;//删除
    }

    public static final class ArticleCommentState{
        public static final int ENABLE=1;
        public static final int DISABLE=0;
    }

    public static final class PubLevel{
        public static final int PRIVATE=5;//私有
        public static final int PASSWORD=6;//需要密码
        public static final int PUBLIC=7;//公开
    }
}
