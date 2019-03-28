package com.hansholdings.manage.service;

import com.hansholdings.manage.entity.Position;
import com.hansholdings.manage.entity.User;

import java.util.List;

/**
 * Created by maozz11347 on 2017/8/18.
 */
public interface IPositionService {

    /**
     * 获取岗位
     * @return
     */
    List<Position> getPositions();

    /**
     * 获取岗位下成员
     * @param uid
     * @return
     */
     List<User> getMembersByPos(String uid);

    Boolean saveOrUpdatePosition(Position pos);

}
