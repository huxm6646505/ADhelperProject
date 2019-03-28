package com.hansholdings.manage.entity;

import com.hansholdings.basic.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "sys_member")
public class Member  extends BaseEntity{

    /**
     * 
     */
    private static final long serialVersionUID = -3157026182471199653L;
    
    //名字
    private String name;
    //性别
    private Integer sex;
    // 生日
    private Date birthday;
    //头像地址
    private String headUrl;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getSex() {
        return sex;
    }
    public void setSex(Integer sex) {
        this.sex = sex;
    }
    public Date getBirthday() {
        return birthday;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    public String getHeadUrl() {
        return headUrl;
    }
    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }
}