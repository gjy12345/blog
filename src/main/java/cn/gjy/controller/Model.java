package cn.gjy.controller;

import java.util.Arrays;

/**
 * @author gujianyang
 * @date 2020/11/25
 */
public class Model {
    private String[] arr;
    private Integer s;

    public String[] getArr() {
        return arr;
    }

    public void setArr(String[] arr) {
        this.arr = arr;
    }

    public Integer getS() {
        return s;
    }

    public void setS(Integer s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return "Model{" +
                "arr=" + Arrays.toString(arr) +
                ", s=" + s +
                '}';
    }
}
