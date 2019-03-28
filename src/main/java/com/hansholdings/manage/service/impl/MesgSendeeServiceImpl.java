package com.hansholdings.manage.service.impl;

import com.hansholdings.basic.dao.IBaseDao;
import com.hansholdings.basic.service.impl.BaseServiceImpl;
import com.hansholdings.manage.dao.IMesgSendeeDao;
import com.hansholdings.manage.entity.MessgSendee;
import com.hansholdings.manage.service.IMesgSendeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by maozz11347 on 2018-9-12.
 */
@Service
public class MesgSendeeServiceImpl  extends BaseServiceImpl<MessgSendee, Long> implements IMesgSendeeService {


    @Autowired
    private IMesgSendeeDao imesgsendeeDao;

    @Override
    public List<MessgSendee> searchByDeptou(String deptou) {
        return imesgsendeeDao.findByDeptou(deptou);
    }

    @Override
    public IBaseDao<MessgSendee, Long> getBaseDao() {
        return this.imesgsendeeDao;
    }
}
