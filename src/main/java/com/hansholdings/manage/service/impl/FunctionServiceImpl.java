package com.hansholdings.manage.service.impl;

import com.hansholdings.basic.dao.IBaseDao;
import com.hansholdings.basic.entity.vo.TreeNode;
import com.hansholdings.basic.service.impl.BaseServiceImpl;
import com.hansholdings.manage.dao.IFunctionDao;
import com.hansholdings.manage.entity.Function;
import com.hansholdings.manage.service.IFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FunctionServiceImpl extends BaseServiceImpl<Function, Long> implements IFunctionService {

    @Autowired
    private IFunctionDao functionDao;

    @Override
    public IBaseDao<Function, Long> getBaseDao() {
        return this.functionDao;
    }

    @Override
    public Integer maxFunctionLevel(Integer type, Long parentId) {
        if (parentId == null || parentId <= 0) {
            return functionDao.maxFunctionLevel(type);
        } else {
            return functionDao.maxFunctionLevel(parentId);
        }
    }

    @Override
    public void save(Function entity) {
        if (entity.getId() == null) {
            Integer level = 0;
            if (entity.getParent() != null && entity.getParent().getId() != null) {
                level = functionDao.maxFunctionLevel(entity.getParent().getId());
                entity.setParent(find(entity.getParent().getId()));
            } else {
                level = functionDao.maxFunctionLevel(entity.getType());
                entity.setParent(null);
            }
            level = (level == null ? 0 : level) + 1;
            entity.setLevel(level);
            entity.setActive(true);
        } else {
            Function entityFunction = find(entity.getId());
            entityFunction.setName(entity.getName());
            entityFunction.setUrl(entity.getUrl());
            entityFunction.setShiroPermission(entity.getShiroPermission());
            entity = entityFunction;
        }
        super.save(entity);
    }

    @Override
    public List<TreeNode> loadTree() {
        List<Function> fArray = findAll();
        List<TreeNode> tnArray = new ArrayList<>(fArray.size());
        for (Function f : fArray) {
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
            tnArray.add(node);
        }
        return tnArray;
    }

    @Override
    public Map<String, Set<Function>> loadFunctionByAdminId(Long adminId) {
        Set<Function> funs = functionDao.loadFunctionByAdminId(adminId);
        Map<String, Set<Function>> mapfuns = new HashMap<>();

        for (Function function : funs) {
            String id = "0";
            if (function.getParent() != null) {
                id = function.getParent().getId().toString();
            }

            if (mapfuns.get(id) != null) {
                mapfuns.get(id).add(function);
            } else {
                Set<Function> tops = new HashSet<>();
                tops.add(function);
                mapfuns.put(id, tops);
            }
        }

        return mapfuns;
    }

    @Override
    public List<Object> loadFunctionRoleByAdminId(Long adminId) {
        return functionDao.loadFunctionRoleByAdminId(adminId);
    }

}
