package com.hansholdings.manage.service.impl;

import com.hansholdings.basic.dao.IBaseDao;
import com.hansholdings.basic.log4j.Logger;
import com.hansholdings.basic.service.impl.BaseServiceImpl;
import com.hansholdings.manage.dao.ILogininfoDao;
import com.hansholdings.manage.entity.Logininfo;
import com.hansholdings.manage.service.ILogininfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by maozz11347 on 2017/12/19.
 */
@Service
public class LogininfoServiceImpl extends BaseServiceImpl<Logininfo, Long> implements ILogininfoService {

    private static Logger logger = Logger.getLogger(LogininfoServiceImpl.class);

    @Autowired
    private ILogininfoDao logininfoDao;

    @Override
    public IBaseDao<Logininfo, Long> getBaseDao() {
        return this.logininfoDao;
    }

    @Override
    public Logininfo findByAdminId(Long adminId) {
        return logininfoDao.findByAdminId(adminId).get(0);
    }
}
