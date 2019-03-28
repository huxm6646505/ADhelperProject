package com.hansholdings.basic.entity.vo;

import java.io.Serializable;

/**
 * 树型节点<br>
 * 
 * @author halo
 * @see
 * @since V1.0
 */
public class TreeNode implements Serializable {

    /**
     */
    private static final long serialVersionUID = -693675736472494761L;

    /**
     * 节点ID
     **/
    private Long              id;

    /**
     * 节点名称
     **/
    private String            name;

    /**
     * 父节点ID
     **/
    private Long              pid;

    /**
     * 组织类型
     **/
    private int               type;

    private int               status           = 1;

    private String            iconSkin         = "";

    private boolean           isParent         = true;

    private boolean           checked          = false;

    private boolean           open             = false;

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    /**
     * 
     */
    public TreeNode() {
        super();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the pid
     */
    public Long getPid() {
        return pid;
    }

    /**
     * @param pid
     *            the pid to set
     */
    public void setPid(Long pid) {
        this.pid = pid;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the iconSkin
     */
    public String getIconSkin() {
        return iconSkin;
    }

    /**
     * @param iconSkin
     *            the iconSkin to set
     */
    public void setIconSkin(String iconSkin) {
        this.iconSkin = iconSkin;
    }

    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean isParent) {
        this.isParent = isParent;
    }

}
