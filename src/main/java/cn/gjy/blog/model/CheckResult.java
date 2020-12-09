package cn.gjy.blog.model;

/**
 * @Author gujianyang
 * @Date 2020/12/9
 * @Class CheckResult
 */
public class CheckResult<T> {
    private int result;
    private T data;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> CheckResult<T> createFailResult(String msg){
        CheckResult<T> result=new CheckResult<>();
        result.setMsg(msg);
        result.setResult(State.FAIL);
        return result;
    }

    public static <T> CheckResult<T> createSuccessResult(T data,String msg){
        CheckResult<T> result=new CheckResult<>();
        result.setMsg(msg);
        result.setResult(State.OK);
        result.setData(data);
        return result;
    }

    public static final class State{
        public static final int OK=1;
        public static final int FAIL=2;
    }
}
