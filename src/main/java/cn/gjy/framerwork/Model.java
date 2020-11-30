package cn.gjy.framerwork;

import cn.gjy.framerwork.annotation.Alias;
import cn.gjy.framerwork.annotation.DateStringFormat;
import cn.gjy.framerwork.annotation.UseHump;

/**
 * @author gujianyang
 * @date 2020/11/23
 */
@UseHump(UseHump.Change.HUMP_TO_LOWER)
public class Model {

    private Integer id;
    private String devId;
    @Alias("sdk")
    private String sdk111;
    private Double os;
    @DateStringFormat()
    private String time;
    private String version;
    private String userName;

    @Override
    public String toString() {
        return "Model{" +
                "id=" + id +
                ", devId='" + devId + '\'' +
                ", sdk111='" + sdk111 + '\'' +
                ", os=" + os +
                ", time='" + time + '\'' +
                ", version='" + version + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getSdk111() {
        return sdk111;
    }

    public void setSdk111(String sdk111) {
        this.sdk111 = sdk111;
    }

    public Double getOs() {
        return os;
    }

    public void setOs(Double os) {
        this.os = os;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
