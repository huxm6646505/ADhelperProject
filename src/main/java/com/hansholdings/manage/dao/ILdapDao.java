package com.hansholdings.manage.dao;

import com.alibaba.fastjson.JSONObject;
import com.hansholdings.manage.entity.OU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by maozz11347 on 2017/9/13.
 */
@Repository
public class ILdapDao {

    @Autowired
    private BaseADDao basedao;

    public JSONObject getLdapInfo(){
        return basedao.getLdapInfo();
    }

    public Boolean saveOrUpdateLdap(JSONObject ldap){

        return  basedao.saveOrUpdateLdap(ldap);
    }


}
