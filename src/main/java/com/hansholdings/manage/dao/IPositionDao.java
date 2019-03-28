package com.hansholdings.manage.dao;

import com.hansholdings.manage.entity.Position;
import com.hansholdings.manage.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by maozz11347 on 2017/8/18.
 */
@Repository
public class IPositionDao {

    @Autowired
    private BaseADDao basedao;

    public List<Position> getPositions(){
        return  basedao.getPositions();
    }

    public  List<User> getMembersByPos(String uid){
        return basedao.getMembersByPos(uid);
    }

    public  Boolean saveOrUpdatePosition(Position pos){
        return basedao.saveOrUpdatePosition(pos);
    }
}
