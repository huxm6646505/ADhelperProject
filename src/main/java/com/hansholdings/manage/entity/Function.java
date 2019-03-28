package com.hansholdings.manage.entity;

import com.hansholdings.basic.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "sys_function")
public class Function extends BaseEntity {
    private static final long       serialVersionUID       = 1L;

    public static Integer           FUNCTION_TYPE_CATEGORY = 1;
    public static Integer           FUNCTION_TYPE_MENU     = 2;
    public static Integer           FUNCTION_TYPE_HREF     = 3;
    public static Integer           FUNCTION_TYPE_BUTTON   = 4;

    /**
     * 功能集的分隔符
     */
    public static final String      FUNC_SPLIT             = "@";

    // fields
    @Column(length = 100)
    private String        name;
    @Column(length = 200)
    private String        url;
    @Column(length = 2, nullable = false)
    private Integer       type;
    // 节点菜单维护
    @Column(length = 200)
    private String        funcs;
    @Column(length = 250)
    private String        description;
    @Column(length = 10, nullable = false)
    private Integer       level;
    @Column(name = "is_active", nullable = false)
    private Boolean       active;
    // 权限
    @Column(length = 200)
    private String                  shiroPermission;

    // many to one
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Function                parent;

    @ManyToMany(mappedBy = "functions")
    private java.util.Set<Role>     roles;
    @OneToMany(mappedBy = "parent")
    private java.util.Set<Function> child;

    public String getShiroPermission() {
        return shiroPermission;
    }

    public void setShiroPermission(String shiroPermission) {
        this.shiroPermission = shiroPermission;
    }

    public String getTreeName() {
        return getName();
    }

    public Function getTreeParent() {
        return getParent();
    }

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
     * Return the value associated with the column: URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set the value related to the column: URL
     *
     * @param url
     *            the URL value
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Return the value associated with the column: FUNCS
     */
    public String getFuncs() {
        return funcs;
    }

    /**
     * Set the value related to the column: FUNCS
     *
     * @param funcs
     *            the FUNCS value
     */
    public void setFuncs(String funcs) {
        this.funcs = funcs;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * Return the value associated with the column: PARENT_ID
     */
    public Function getParent() {
        return parent;
    }

    /**
     * Set the value related to the column: PARENT_ID
     *
     * @param parent
     *            the PARENT_ID value
     */
    public void setParent(Function parent) {
        this.parent = parent;
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

    /**
     * Return the value associated with the column: child
     */
    public java.util.Set<Function> getChild() {
        return child;
    }

    /**
     * Set the value related to the column: child
     *
     * @param child
     *            the child value
     */
    public void setChild(java.util.Set<Function> child) {
        this.child = child;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        Function other = (Function) obj;
        if (getId() == null) {
            if (other.getId() != null) return false;
        } else if (!getId().equals(other.getId())) return false;
        return true;
    }

    public String toString() {
        return super.toString();
    }
}
