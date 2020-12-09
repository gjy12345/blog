package cn.gjy.blog.model;

import java.util.List;

/**
 * @Author gujianyang
 * @Date 2020/12/8
 * @Class BaseWeAdminModel
 * 后台前端框架的实体
 */
public class BaseWeAdminModel <T> {

    private int status;
    private String msg;
    private List<T> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
