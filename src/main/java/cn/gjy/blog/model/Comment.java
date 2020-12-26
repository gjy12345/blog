package cn.gjy.blog.model;

import cn.gjy.blog.framework.annotation.Alias;

/**
 * @Author gujianyang
 * @Date 2020/12/15
 * @Class Comment
 * 文章评论
 */
public class Comment {
    private Integer id;
    @Alias("article_id")
    private Integer articleId;
    @Alias("user_id")
    private Integer userId;
    private String content;
    @Alias("common_time")
    private String commonTime;
    @Alias("create_time_l")
    private Long createTimeL;
    @Alias("user_type")
    private Integer userType;
    private String articlePassword;
    private String userName;
    private boolean canDelete;
    private String articleTitle;
    private String articleUrl;

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getArticlePassword() {
        return articlePassword;
    }

    public void setArticlePassword(String articlePassword) {
        this.articlePassword = articlePassword;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCommonTime() {
        return commonTime;
    }

    public void setCommonTime(String commonTime) {
        this.commonTime = commonTime;
    }

    public Long getCreateTimeL() {
        return createTimeL;
    }

    public void setCreateTimeL(Long createTimeL) {
        this.createTimeL = createTimeL;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }


    public static class CommentUserType{
        public static final int ADMIN=1;
        public static final int USER=0;
        public static final int AUTHOR=2;
    }
}
