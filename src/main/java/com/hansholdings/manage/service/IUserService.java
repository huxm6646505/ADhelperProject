package com.hansholdings.manage.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hansholdings.manage.entity.QueryCondition;
import com.hansholdings.manage.entity.User;

import java.util.List;

/**
 * Created by maozz11347 on 2017/7/17.
 */
public interface IUserService {

     List<User> getAllUser(QueryCondition query);

     boolean saveOrUpdateUser(User user);

     boolean deleteUser(String uid);

      boolean  chagePasword(User user);

     int getADUserSize(QueryCondition query);

     List<User> getUserByQuery(User user);

     JSONObject uploadUser(byte[] bytes, String filetype, String service);

      JSONObject exportUser(QueryCondition query);

}
