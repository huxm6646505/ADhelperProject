package com.hansholdings.manage.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by maozz11347 on 2017/7/17.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 6666370934999162273L;
    private String objectGUID;
    private String distinguishedName;
    private String name;
    private String sAMAccountName;
    private String userPrincipalName;
    @JSONField(serialize=false)
    private String whenChanged;
    @JSONField(serialize=false)
    private String whenCreated;
    private String unicodePwd;
    private String mail;
    private String telephoneNumber;
    private String physicalDeliveryOfficeName;
    private String initials;
    private String department;
    private String userAccountControl;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date pwdLastSet;

    public String getObjectGUID() {
        return objectGUID;
    }

    public void setObjectGUID(String objectGUID) {
        this.objectGUID = objectGUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getsAMAccountName() {
        return sAMAccountName;
    }

    public void setsAMAccountName(String sAMAccountName) {
        this.sAMAccountName = sAMAccountName;
    }

    public String getUserPrincipalName() {
        return userPrincipalName;
    }

    public void setUserPrincipalName(String userPrincipalName) {
        this.userPrincipalName = userPrincipalName;
    }

    public String getWhenChanged() {
        return whenChanged;
    }

    public void setWhenChanged(String whenChanged) {
        this.whenChanged = whenChanged;
    }

    public String getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(String whenCreated) {
        this.whenCreated = whenCreated;
    }

    public String getUnicodePwd() {
        return unicodePwd;
    }

    public void setUnicodePwd(String unicodePwd) {
        this.unicodePwd = unicodePwd;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getPhysicalDeliveryOfficeName() {
        return physicalDeliveryOfficeName;
    }

    public void setPhysicalDeliveryOfficeName(String physicalDeliveryOfficeName) {
        this.physicalDeliveryOfficeName = physicalDeliveryOfficeName;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUserAccountControl() {
        return userAccountControl;
    }

    public void setUserAccountControl(String userAccountControl) {
        this.userAccountControl = userAccountControl;
    }

    public String getDistinguishedName() {
        return distinguishedName;
    }

    public void setDistinguishedName(String distinguishedName) {
        this.distinguishedName = distinguishedName;
    }

    public Date getPwdLastSet() {
        return pwdLastSet;
    }

    public void setPwdLastSet(Date pwdLastSet) {
        this.pwdLastSet = pwdLastSet;
    }
}
