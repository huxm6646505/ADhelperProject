package com.hansholdings.manage.entity;

import com.hansholdings.basic.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by maozz11347 on 2018-9-12.
 */

@Entity
@Table(name = "sys_messgsendee")
public class MessgSendee extends BaseEntity {

    private static final long   serialVersionUID = 1L;

    private String deptou;
    private String sendee;

    public String getDeptou() {
        return deptou;
    }

    public void setDeptou(String deptou) {
        this.deptou = deptou;
    }

    public String getSendee() {
        return sendee;
    }

    public void setSendee(String sendee) {
        this.sendee = sendee;
    }
}
