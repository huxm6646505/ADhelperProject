package com.hansholdings.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hansholdings.basic.log4j.Logger;
import com.hansholdings.manage.dao.ILdapDao;
import com.hansholdings.manage.service.ILdapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by maozz11347 on 2017/7/17.
 */
@Service
public class LdapServiceImpl implements ILdapService {

    private static Logger logger = Logger.getLogger(LdapServiceImpl.class);

    @Autowired
    private ILdapDao ldapDao;


    @Override
    public JSONObject getLdapInfo() {
        return ldapDao.getLdapInfo();
    }

    @Override
    public Boolean saveOrUpdateLdap(JSONObject ldap) {
        return ldapDao.saveOrUpdateLdap(ldap);
    }
}
