package com.hansholdings.manage.service;

import com.alibaba.fastjson.JSONObject;
import com.hansholdings.basic.entity.vo.OuTreeNode;
import com.hansholdings.manage.entity.OU;
import com.hansholdings.manage.entity.QueryCondition;
import com.hansholdings.manage.entity.User;

import java.util.List;

/**
 * Created by maozz11347 on 2017/7/17.
 */
public interface ILdapService {

     JSONObject getLdapInfo();

     Boolean saveOrUpdateLdap(JSONObject ldap);
}
