package com.hansholdings.manage.dao;

import com.hansholdings.basic.dao.IBaseDao;
import com.hansholdings.manage.entity.Logininfo;
import com.hansholdings.manage.entity.Operationinfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author maozz11347
 * @date 2017/12/21
 */
@Repository
public interface IOperationDao extends IBaseDao<Operationinfo, Long> {

    @Query("from Operationinfo a where a.uuid = ?1 ")
    Operationinfo findByUuid(String uuid);
}
