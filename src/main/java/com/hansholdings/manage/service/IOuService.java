package com.hansholdings.manage.service;

import com.hansholdings.basic.entity.vo.OuTreeNode;
import com.hansholdings.manage.entity.OU;
import com.hansholdings.manage.entity.QueryCondition;
import com.hansholdings.manage.entity.User;

import java.util.List;

/**
 * Created by maozz11347 on 2017/7/17.
 */
public interface IOuService {


     List<OuTreeNode> loadTreeAndMarkOUs(String rootOU);

     List<User> getUsers(QueryCondition query);

     List<OU> getOuByQuery(QueryCondition query);

     boolean saveOrUpdateOu(OU ou);

     int getOuSize(QueryCondition query);

     List<OU> getAllOU(String rootOU);

     Boolean  getOuByQuery(OU query);

      List<OU> getOUBydes(String rootOU,String description);

}
