package com.hansholdings.basic.entity;

import com.hansholdings.basic.listener.BaseEntityListener;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@EntityListeners({ BaseEntityListener.class })
public abstract class BaseEntity extends AbstractAuditable<User, Long> {

    private static final long serialVersionUID = -67188388306700736L;
    private String delFlag = DEL_FLAG_NORMAL; // 删除标记（0：正常；1：删除；2：审核）

    public static final String FIELD_DEL_FLAG = "delFlag";
    public static final String DEL_FLAG_NORMAL = "0";
    public static final String DEL_FLAG_DELETE = "1";
    public static final String DEL_FLAG_AUDIT = "2";


    public void setId(Long id) {
        super.setId(id);
    }

    @Length(min = 1, max = 1)
    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

}