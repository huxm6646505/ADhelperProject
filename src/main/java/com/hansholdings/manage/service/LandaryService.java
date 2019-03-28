package com.hansholdings.manage.service;

import com.alibaba.fastjson.JSONObject;

/**
 *
 * @author maozz11347
 * @date 2018/1/2
 */
public interface LandaryService {
    /**
     * 入职接口
     * @param jsonUser
     * @return
     */
    JSONObject addUser(JSONObject jsonUser);

    /**
     * 离职接口
     * @param jsonUser
     * @return
     */
    JSONObject updateUserState(JSONObject jsonUser);

    /**
     * 调职接口
     * @param jsonUser
     * @return
     */
    JSONObject updateUserOU(JSONObject jsonUser);

    /**更新接口
     * @param jsonUser
     * @return
     */
    JSONObject updateUserInfo(JSONObject jsonUser);
}
