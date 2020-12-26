package cn.gjy.blog.system;

import cn.gjy.blog.framework.annotation.Component;
import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author gujianyang
 * @Date 2020/12/21
 * @Class SystemInfoRunnable
 * 获取系统的参数
 */
@Component
public class SystemInfoRunnable implements Runnable{

    private static final int WAIT_TIME=2000;//休眠两秒获取

    private MemoryInfo memoryInfo;

    private CpuInfo cpuInfo;

    private JvmInfo jvmInfo;

    private Thread thread;

    private static SystemInfoRunnable instance=null;


    public SystemInfoRunnable(){
        if(instance!=null)
            throw new RuntimeException("已存在SystemInfoRunnable实例");
        instance=this;
        thread=new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        this.memoryInfo=new MemoryInfo();
        this.jvmInfo=new JvmInfo();
        this.cpuInfo=new CpuInfo();
        OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        initSystemInfo();
        while (!Thread.currentThread().isInterrupted()){
            setMemoryInfo(operatingSystemMXBean);
            setJvmInfo();
            updateSystemInfo();
            try {
                Thread.sleep(WAIT_TIME);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    private void updateSystemInfo() {
        systemInfoMap.put("程序已用内存",jvmInfo.used);
        systemInfoMap.put("程序闲置内存",jvmInfo.free);
        systemInfoMap.put("程序最大可用",jvmInfo.total);
        systemInfoMap.put("系统内存",memoryInfo.total);
        systemInfoMap.put("系统可用",memoryInfo.free);
        systemInfoMap.put("系统已用",memoryInfo.used);

    }

    private void setJvmInfo() {
        long total=Runtime.getRuntime().totalMemory();
        long max=Runtime.getRuntime().maxMemory();
        jvmInfo.setTotal(convertFileSize(max));
        jvmInfo.setUsed(convertFileSize(total));
        jvmInfo.setFree(convertFileSize(Runtime.getRuntime().freeMemory()));
        jvmInfo.setUseRate(total*1.0/max);
    }

    private void setMemoryInfo(OperatingSystemMXBean operatingSystemMXBean){
        long physicalFree = operatingSystemMXBean.getFreePhysicalMemorySize() ;
        long physicalTotal = operatingSystemMXBean.getTotalPhysicalMemorySize() ;
        long physicalUse = physicalTotal - physicalFree;
        memoryInfo.setTotal(convertFileSize(physicalTotal));
        memoryInfo.setUsed(convertFileSize(physicalUse));
        memoryInfo.setFree(convertFileSize(physicalFree));
    }

//    private List<S>
    private Map<String,Object> systemInfoMap=new LinkedHashMap<>();

    public Map<String,Object> getSystemInfos(){
        return systemInfoMap;
    }

    public void initSystemInfo(){
        systemInfoMap.clear();
        Properties properties = System.getProperties();
        systemInfoMap.put("系统名",properties.getProperty("os.name"));
        systemInfoMap.put("架构",properties.getProperty("os.arch"));
        systemInfoMap.put("Java版本",properties.getProperty("java.version"));
        systemInfoMap.put("Jvm版本",properties.getProperty("java.vm.name"));
    }

//
    /**
     * 字节转换
     *
     * @param size 字节大小
     * @return 转换后值
     */
    public String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else {
            return String.format("%d B", size);
        }
    }

    public void stopWork() {
        if(thread!=null)
            thread.interrupt();
    }

    private static class MemoryInfo{
        private String total;
        private String free;
        private String used;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getFree() {
            return free;
        }

        public void setFree(String free) {
            this.free = free;
        }

        public String getUsed() {
            return used;
        }

        public void setUsed(String used) {
            this.used = used;
        }
    }
    private static class CpuInfo{
        private int cpuNum;
        private long total;
        private long sys;
        private long used;
        private long wait;
        private long free;

        public int getCpuNum() {
            return cpuNum;
        }

        public void setCpuNum(int cpuNum) {
            this.cpuNum = cpuNum;
        }

        public long getTotal() {
            return total;
        }

        public void setTotal(long total) {
            this.total = total;
        }

        public long getSys() {
            return sys;
        }

        public void setSys(long sys) {
            this.sys = sys;
        }

        public long getUsed() {
            return used;
        }

        public void setUsed(long used) {
            this.used = used;
        }

        public long getWait() {
            return wait;
        }

        public void setWait(long wait) {
            this.wait = wait;
        }

        public long getFree() {
            return free;
        }

        public void setFree(long free) {
            this.free = free;
        }
    }
    private static class JvmInfo{
        private String total;
        private String free;
        private String used;
        private double useRate;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getFree() {
            return free;
        }

        public void setFree(String free) {
            this.free = free;
        }

        public String getUsed() {
            return used;
        }

        public void setUsed(String used) {
            this.used = used;
        }

        public double getUseRate() {
            return useRate;
        }

        public void setUseRate(double useRate) {
            this.useRate = useRate;
        }
    }
}
