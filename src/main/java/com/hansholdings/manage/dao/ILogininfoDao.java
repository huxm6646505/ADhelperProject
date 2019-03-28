package com.hansholdings.manage.dao;

import com.hansholdings.basic.dao.IBaseDao;
import com.hansholdings.manage.entity.Logininfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by maozz11347 on 2017/12/19.
 */
@Repository
public interface ILogininfoDao extends IBaseDao<Logininfo, Long> {
    @Query("from Logininfo a where a.adminId = ?1 order by a.loginTime desc")
    List<Logininfo> findByAdminId(Long adminId);
}
