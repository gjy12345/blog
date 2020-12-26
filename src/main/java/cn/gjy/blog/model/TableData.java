package cn.gjy.blog.model;
import java.util.List;

/**
 * @Author gujianyang
 * @Date 2020/12/11
 * @Class TableData
 */
public class TableData <T>{
    private int total;//所有条数
    private T data;//数据

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> TableData<T> emptyData(T t){
        TableData<T> tableData = new TableData<>();
        tableData.setData(t);
        tableData.setTotal(0);
        return tableData;
    }
}
