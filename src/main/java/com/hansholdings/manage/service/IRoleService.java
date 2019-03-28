package com.hansholdings.manage.service;



import com.hansholdings.basic.entity.vo.TreeNode;
import com.hansholdings.basic.service.IBaseService;
import com.hansholdings.manage.entity.Function;
import com.hansholdings.manage.entity.Role;

import java.util.List;
import java.util.Set;

public interface IRoleService extends IBaseService<Role, Long> {

    void saveOrUpdate(Role role);

    /**
     * 加载tree并且标记角色所属菜单
     * 
     * @param roleId
     * @return
     */
    List<TreeNode> loadTreeAndMarkRoleFunctions(Long roleId);

    Boolean saveRoleFunctions(Long roleId, Set<Function> functions);
}
