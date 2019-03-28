package com.hansholdings.manage.service;

import com.hansholdings.basic.service.IBaseService;
import com.hansholdings.manage.entity.Admin;

public interface IAdminService extends IBaseService<Admin, Long> {

    Admin findByUsername(String username);

    void saveOrUpdate(Admin admin);

    Boolean saveAdminRoles(Long id, Long[] roles);

    Admin findByManager(String manager);

    boolean loginValidate(String sAMAccountName,String password);

}
