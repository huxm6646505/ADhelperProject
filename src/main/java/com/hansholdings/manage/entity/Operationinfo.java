package com.hansholdings.manage.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.hansholdings.basic.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 *
 * @author maozz11347
 * @date 2017/12/20
 */
@Entity
@Table(name="sys_Operationinfo")
public class Operationinfo extends BaseEntity {
    private String userName;
    private String operateStype;
    private String serviceStype;
    private String originalValue;
    private String currentValue;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date operationTime;
    private String uuid;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOperateStype() {
        return operateStype;
    }

    public void setOperateStype(String operateStype) {
        this.operateStype = operateStype;
    }

    public String getServiceStype() {
        return serviceStype;
    }

    public void setServiceStype(String serviceStype) {
        this.serviceStype = serviceStype;
    }

    public String getOriginalValue() {
        return originalValue;
    }

    public void setOriginalValue(String originalValue) {
        this.originalValue = originalValue;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
