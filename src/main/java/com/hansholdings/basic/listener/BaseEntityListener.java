package com.hansholdings.basic.listener;

import com.hansholdings.basic.entity.BaseEntity;
import com.hansholdings.basic.utils.UserUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Component("baseEntityListener")
public class BaseEntityListener {

    /**
     * Before add entity, init createDate and updateDate
     * 
     * @param baseEntity
     */
    @PrePersist
    public void initEntity(BaseEntity baseEntity) {
        baseEntity.setCreatedDate(DateTime.now());
        baseEntity.setCreatedBy(UserUtils.getCurrentUser());
        baseEntity.setLastModifiedDate(DateTime.now());
        baseEntity.setLastModifiedBy(UserUtils.getCurrentUser());
    }

    /**
     * Before update entity ,set updateDate
     */
    @PreUpdate
    public void updateEntity(BaseEntity baseEntity) {
        baseEntity.setLastModifiedDate(DateTime.now());
        baseEntity.setLastModifiedBy(UserUtils.getCurrentUser());
    }
}
