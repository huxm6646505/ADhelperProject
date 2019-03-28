package com.hansholdings.manage.dao;

import com.hansholdings.basic.dao.IBaseDao;
import com.hansholdings.manage.entity.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleDao extends IBaseDao<Role, Long> {

}
