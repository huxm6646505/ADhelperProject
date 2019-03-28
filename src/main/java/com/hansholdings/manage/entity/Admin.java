package com.hansholdings.manage.entity;

import com.hansholdings.basic.entity.BaseEntity;
import com.hansholdings.basic.entity.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sys_admin")
public class Admin extends BaseEntity {
    private static final long   serialVersionUID = 1L;
    // fields
    private java.util.Date      loginTime;
    private Integer   loginCount;

    // many to one
    @OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User                user;

    // many to one
    @OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member              member;

    // collections
    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinTable(name = "sys_admin_role", joinColumns = { @JoinColumn(name = "admin_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
    private java.util.Set<Role> roles;

    //one to many
    @OneToMany(cascade={CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name="admin_id")
    private java.util.Set<Ourole> ouroles;

    // 被锁定
    private Integer    locked=0;

    /**
     * 获得用户(登录)名
     *
     * @return
     */
    public String getUserName() {
        if (getUser() != null) {
            return getUser().getUsername();
        } else {
            return null;
        }
    }

    /**
     * 获得登录名
     *
     * @return
     */
    public String getLoginName() {
        if (getUser() != null) {
            return getUser().getUsername();
        } else {
            return null;
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public java.util.Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(java.util.Date loginTime) {
        this.loginTime = loginTime;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    /**
     * Return the value associated with the column: roles
     */
    public java.util.Set<Role> getRoles() {
        return roles;
    }

    /**
     * Set the value related to the column: roles
     * 
     * @param roles
     *            the roles value
     */
    public void setRoles(java.util.Set<Role> roles) {
        this.roles = roles;
    }

    public void addToRole(Role role) {
        if (getRoles() == null) {
            HashSet<Role> h = new HashSet<>();
            h.add(role);
            setRoles(h);
        } else {
            getRoles().add(role);
        }
    }

    public boolean equals(Object obj) {
        if (null == obj) return false;
        if (!(obj instanceof Admin)) return false;
        else {
            Admin admin = (Admin) obj;
            if (null == this.getId() || null == admin.getId()) return false;
            else return (this.getId().equals(admin.getId()));
        }
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    public Integer getLocked() {
        return locked;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Set<Ourole> getOuroles() {
        return ouroles;
    }

    public void setOuroles(Set<Ourole> ouroles) {
        this.ouroles = ouroles;
    }
}
