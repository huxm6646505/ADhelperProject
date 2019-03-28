package com.hansholdings.manage.service;

import com.hansholdings.basic.entity.vo.TreeNode;
import com.hansholdings.basic.service.IBaseService;
import com.hansholdings.manage.entity.Function;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IFunctionService extends IBaseService<Function, Long> {

    Integer maxFunctionLevel(Integer type, Long parentId);

    List<TreeNode> loadTree();

    public Map<String, Set<Function>> loadFunctionByAdminId(Long adminId);

    public List<Object> loadFunctionRoleByAdminId(Long adminId);
}
