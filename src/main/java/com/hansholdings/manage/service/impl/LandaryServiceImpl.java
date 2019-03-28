package com.hansholdings.manage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hansholdings.manage.dao.BaseADDao;
import com.hansholdings.manage.service.LandaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by maozz11347 on 2018/1/2.
 */
@Service
public class LandaryServiceImpl implements LandaryService {

    @Autowired
    private BaseADDao baseADDao;
    @Override
    public JSONObject addUser(JSONObject jsonUser) {
        return baseADDao.addUser(jsonUser);
    }

    @Override
    public JSONObject updateUserState(JSONObject jsonUser) {
        return baseADDao.updateUserState(jsonUser);
    }

    @Override
    public JSONObject updateUserOU(JSONObject jsonUser) {
        return baseADDao.updateUserOU(jsonUser);
    }

    @Override
    public JSONObject updateUserInfo(JSONObject jsonUser) {
        return baseADDao.updateUserInfo(jsonUser);
    }
}
