package com.hansholdings.manage.dao;

import com.hansholdings.basic.dao.IBaseDao;
import com.hansholdings.manage.entity.Admin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IAdminDao extends IBaseDao<Admin, Long> {

    @Query("from Admin a where a.user.username = ?1")
    Admin findByUsername(String username);

    @Query("from Admin a where a.user.manager like %?1% and a.locked=1")
    Admin findByManager(String manager);

}
