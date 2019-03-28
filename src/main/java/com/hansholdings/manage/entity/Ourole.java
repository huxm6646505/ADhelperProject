package com.hansholdings.manage.entity;

import com.hansholdings.basic.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by maozz11347 on 2017/11/9.
 */
@Entity
@Table(name = "sys_ourole")
public class Ourole extends BaseEntity {
    private String oudn;
    private Integer admin_id;
    private String postalCode;
    private String managedByName;

    public String getOudn() {
        return oudn;
    }

    public void setOudn(String oudn) {
        this.oudn = oudn;
    }

    public Integer getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(Integer admin_id) {
        this.admin_id = admin_id;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getManagedByName() {
        return managedByName;
    }

    public void setManagedByName(String managedByName) {
        this.managedByName = managedByName;
    }
}
