package cn.gjy.blog.test;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;
/**
 * @Author gujianyang
 * @Date 2020/12/20
 * @Class TestOsHi
 */
public class TestOsHi {


    private static OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    public static int cpuLoad() {
        double cpuLoad = osmxb.getSystemCpuLoad();
        int percentCpuLoad = (int) (cpuLoad * 100);
        return percentCpuLoad;

    }
    public static void main(String[] args) throws InterruptedException {
        System.getProperties().keySet().forEach(key->{
            System.out.println(key +"  "+System.getProperties().getProperty((String) key));
        });
    }
}
