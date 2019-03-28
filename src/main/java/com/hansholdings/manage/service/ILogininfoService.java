package com.hansholdings.manage.service;

import com.hansholdings.basic.service.IBaseService;
import com.hansholdings.manage.entity.Logininfo;

/**
 * Created by maozz11347 on 2017/12/19.
 */
public interface ILogininfoService extends IBaseService<Logininfo, Long> {

    Logininfo findByAdminId(Long adminId);
}
