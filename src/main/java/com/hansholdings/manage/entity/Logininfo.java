package com.hansholdings.manage.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.hansholdings.basic.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 *
 * @author maozz11347
 * @date 2017/12/19
 */
@Entity
@Table(name = "sys_Logininfo")
public class Logininfo extends BaseEntity {

    private static final long   serialVersionUID = 1L;

    private  String userName;
    private  String ipAddre;
    private  String browerVersion;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private  Date loginTime;
    @JSONField (format="yyyy-MM-dd HH:mm:ss")
    private  Date exitTime;
    private  long adminId;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIpAddre() {
        return ipAddre;
    }

    public void setIpAddre(String ipAddre) {
        this.ipAddre = ipAddre;
    }

    public String getBrowerVersion() {
        return browerVersion;
    }

    public void setBrowerVersion(String browerVersion) {
        this.browerVersion = browerVersion;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getExitTime() {
        return exitTime;
    }

    public void setExitTime(Date exitTime) {
        this.exitTime = exitTime;
    }

    public long getAdminId() {
        return adminId;
    }

    public void setAdminId(long adminId) {
        this.adminId = adminId;
    }
}
