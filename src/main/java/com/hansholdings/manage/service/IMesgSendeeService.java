package com.hansholdings.manage.service;

import com.hansholdings.basic.service.IBaseService;
import com.hansholdings.manage.entity.MessgSendee;

import java.util.List;

/**
 * Created by maozz11347 on 2018-9-12.
 */

public interface IMesgSendeeService  extends IBaseService<MessgSendee, Long> {

    /**
     * 根据部门查找
     * @param deotou
     * @return
     */
    public List<MessgSendee> searchByDeptou(String deotou);
}
