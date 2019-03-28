package com.hansholdings.manage.dao;

import com.hansholdings.basic.dao.IBaseDao;
import com.hansholdings.manage.entity.Function;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface IFunctionDao extends IBaseDao<Function, Long> {

    @Query(nativeQuery = true, value = "select level from sys_function where type = ?1 order by level desc limit 1")
    public Integer maxFunctionLevel(Integer type);

    @Query(nativeQuery = true, value = "select level from sys_function t where t.parent_id = ?1 order by level desc limit 1")
    public Integer maxFunctionLevel(Long parentId);

    @Query(nativeQuery = true, value = "SELECT sf.* FROM sys_admin_role sar, sys_role sr, sys_role_function srf, sys_function sf WHERE sar.role_id = sr.id AND sr.id = srf.role_id AND srf.function_id = sf.id AND sar.admin_id = :adminId  order by type asc")
    public Set <Function> loadFunctionByAdminId(@Param("adminId") Long adminId);

    @Query(nativeQuery = true, value = "SELECT sf.name,sf.url FROM sys_admin_role sar, sys_role sr, sys_role_function srf, sys_function sf WHERE sar.role_id = sr.id AND sr.id = srf.role_id AND srf.function_id = sf.id AND sar.admin_id = :adminId  order by type asc")
    public List<Object> loadFunctionRoleByAdminId(@Param("adminId") Long adminId);
}
