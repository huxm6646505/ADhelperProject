package com.hansholdings.manage.dao;

import com.hansholdings.basic.dao.IBaseDao;
import com.hansholdings.manage.entity.MessgSendee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by maozz11347 on 2018-9-12.
 */
@Repository
public interface IMesgSendeeDao extends IBaseDao<MessgSendee, Long> {

    @Query("FROM MessgSendee m WHERE instr(:deptou,m.deptou)>0 ")
    List<MessgSendee> findByDeptou(@Param("deptou") String deptou);
}
