package com.hansholdings.manage.service;

import com.hansholdings.basic.service.IBaseService;
import com.hansholdings.manage.entity.Ourole;

/**
 * Created by maozz11347 on 2017/11/10.
 */
public interface IOuroleService extends IBaseService<Ourole, Long> {
    Boolean saveOrUpdate(Ourole ourole);
}
