package cn.gjy.blog.model;

import cn.gjy.blog.framework.annotation.Alias;

/**
 * @Author gujianyang
 * @Date 2020/12/9
 * @Class SysOperation
 * 用户操作类
 */
public class SysOperation {
    private Integer id;
    @Alias("user_id")
    private Integer userId;
    private String operation;
    @Alias("operation_type")
    private Integer operationType;
    @Alias("operation_time")
    private String operationTime;
    private String ip;
    private String client;

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

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public String getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(String operationTime) {
        this.operationTime = operationTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public static final class OperationType{
        public static final int CREATE_ACCOUNT=1;//创建账号
        public static final int CREATE_NEW_BOLG=2;//创建博客
        public static final int EDIT_BOLG=3;//修改博客
        public static final int DELETE_BOLG=4;//删除博客
        public static final int AGREE_BLOG=5;//赞同博客 点赞
        public static final int FOLLOW=6;//关注用户
        public static final int LOOK_BLOG=7;//浏览博客
    }
}
