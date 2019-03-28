package com.hansholdings.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.hansholdings.basic.entity.vo.OuTreeNode;
import com.hansholdings.basic.log4j.Logger;
import com.hansholdings.manage.dao.IOUDao;
import com.hansholdings.manage.entity.OU;
import com.hansholdings.manage.entity.QueryCondition;
import com.hansholdings.manage.entity.User;
import com.hansholdings.manage.service.IOuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maozz11347 on 2017/7/17.
 */
@Service
public class OUServiceImpl  implements IOuService {

    private static Logger  logger = Logger.getLogger(OUServiceImpl.class);

    @Autowired
    private IOUDao ouDao;

    /**
     * 获取组织树节点下用户
     * @param query
     * @return
     */
    @Override
    public List<User> getUsers(QueryCondition query) {  return ouDao.getUsers(query); }

    /**
     * 获取组织树节点下OU
     * @param query
     * @return
     */
    @Override
    public List<OU> getOuByQuery(QueryCondition query) {
        return ouDao.getOuByQuery(query);
    }

    /**
     * 组装组织树
     * @return
     */
    @Override
    public List<OuTreeNode> loadTreeAndMarkOUs(String rootOU) {
        List<OU> ous= ouDao.getAllOU(rootOU);
        System.out.println(JSON.toJSONString(ous).toString());
        List<OuTreeNode> tnArray = new ArrayList<>(ous.size());
        for (OU f : ous) {
            OuTreeNode node = new OuTreeNode();
            node.setId(f.getUid());
            node.setPid(f.getPid());
            node.setName(f.getName());
            node.setNocheck(true);
         /*   if (f.getChild() != null) {
                node.setParent(true);
            }*/
           // node.setChecked(false);
            tnArray.add(node);
        }
        return tnArray;
    }

    /**
     * 新增修改OU组织
     * @param ou
     * @return
     */
    @Override
    public boolean saveOrUpdateOu(OU ou) {
        return ouDao.saveOrUpdateOu(ou);
    }

    @Override
    public int getOuSize(QueryCondition query) {
        return ouDao.getOuSize(query);
    }

    @Override
    public List<OU> getAllOU(String rootOU) {
        return ouDao.getAllOU(rootOU);
    }

    @Override
    public Boolean getOuByQuery(OU query) {
        return ouDao.getOuByQuery(query);
    }

    @Override
    public List<OU> getOUBydes(String rootOU,String description) {
        return ouDao.getOUBydes(rootOU,description);
    }


}
