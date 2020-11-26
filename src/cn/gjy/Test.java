package cn.gjy;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        int count=0;
        double a=100;
        double sum=a;
        while (count<10){
            a/=2;
            sum+=a;
            count++;
        }
        System.out.println(sum);
        System.out.println(a);
    }
}
