package com.hansholdings.manage.entity;

import com.hansholdings.basic.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "sys_role")
public class Role extends BaseEntity {

    /**
     * 
     */
    private static final long       serialVersionUID = 820694530776191567L;

    // fields
    private String        name;
    private String        description;

    // collections
    @ManyToMany(mappedBy = "roles")
    private java.util.Set<Admin>    admins;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "sys_role_function", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = { @JoinColumn(name = "function_id") })
    private java.util.Set<Function> functions;

    /**
     * Return the value associated with the column: NAME
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value related to the column: NAME
     *
     * @param name
     *            the NAME value
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return the value associated with the column: DESCRIPTION
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the value related to the column: DESCRIPTION
     *
     * @param description
     *            the DESCRIPTION value
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Return the value associated with the column: admins
     */
    public java.util.Set<Admin> getAdmins() {
        return admins;
    }

    /**
     * Set the value related to the column: admins
     * 
     * @param admins
     *            the admins value
     */
    public void setAdmins(java.util.Set<Admin> admins) {
        this.admins = admins;
    }

    /**
     * Return the value associated with the column: functions
     */
    public java.util.Set<Function> getFunctions() {
        return functions;
    }

    /**
     * Set the value related to the column: functions
     * 
     * @param functions
     *            the functions value
     */
    public void setFunctions(java.util.Set<Function> functions) {
        this.functions = functions;
    }
}
