package com.hansholdings.manage.service.impl;

import com.hansholdings.basic.log4j.Logger;
import com.hansholdings.manage.dao.IPositionDao;
import com.hansholdings.manage.entity.Position;
import com.hansholdings.manage.entity.User;
import com.hansholdings.manage.service.IPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by maozz11347 on 2017/8/18.
 */
@Service
public class PositionServiceImpl implements IPositionService {

    private static Logger logger = Logger.getLogger(PositionServiceImpl.class);

    @Autowired
    private IPositionDao positionDao;

    @Override
    public List<Position> getPositions() {
        return positionDao.getPositions();
    }

    @Override
    public List<User> getMembersByPos(String uid) {
        return positionDao.getMembersByPos(uid);
    }

    @Override
    public Boolean saveOrUpdatePosition(Position pos) {
        return positionDao.saveOrUpdatePosition(pos);
    }
}
