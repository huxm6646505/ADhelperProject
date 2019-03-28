package com.hansholdings.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.hansholdings.basic.dao.IBaseDao;
import com.hansholdings.basic.entity.vo.TreeNode;
import com.hansholdings.basic.log4j.Logger;
import com.hansholdings.basic.service.impl.BaseServiceImpl;
import com.hansholdings.manage.dao.IRoleDao;
import com.hansholdings.manage.entity.Function;
import com.hansholdings.manage.entity.Role;
import com.hansholdings.manage.service.IFunctionService;
import com.hansholdings.manage.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, Long> implements IRoleService {

    private static Logger    logger = Logger.getLogger(RoleServiceImpl.class);

    @Autowired
    private IRoleDao         roleDao;

    @Autowired
    private IFunctionService functionService;

    @Override
    public IBaseDao<Role, Long> getBaseDao() {
        return this.roleDao;
    }

    @Override
    public void saveOrUpdate(Role role) {
        if (role.getId() != null) {
            Role roleOne = find(role.getId());
            roleOne.setName(role.getName());
            update(roleOne);
        } else {
            save(role);
        }
    }

    @Override
    public List<TreeNode> loadTreeAndMarkRoleFunctions(Long roleId) {
        Role role = find(roleId);
        Set<Function> roleFunctionSet = role.getFunctions();

        List<Function> functionArray = functionService.findAll();
        List<TreeNode> tnArray = new ArrayList<>(functionArray.size());
        for (Function f : functionArray) {
            TreeNode node = new TreeNode();
            node.setId(f.getId());
            if (f.getParent() == null) {
                node.setPid(-1L);
            } else {
                node.setPid(f.getParent().getId());
            }
            node.setName(f.getName());
            if (f.getChild() != null) {
                node.setParent(true);
            }

            if (roleFunctionSet.contains(f)) {
                node.setChecked(true);
            }
            tnArray.add(node);
        }

        return tnArray;
    }

    @Override
    public Boolean saveRoleFunctions(Long roleId, Set<Function> functions) {
        try {
            Role role = find(roleId);
            Set<Function> newFunctions = new HashSet<>(functions.size());
            for (Function function : functions) {
                newFunctions.add(functionService.find(function.getId()));
            }
            role.setFunctions(newFunctions);
            super.save(role);
        } catch (Exception e) {
            logger.error("Exception params:[roleId:%s,nodes:%s]", e, roleId.toString(), JSON.toJSONString(functions));
            return false;
        }
        return true;
    }

}
