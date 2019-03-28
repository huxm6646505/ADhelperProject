package com.hansholdings.manage.dao;

import com.hansholdings.manage.entity.OU;
import com.hansholdings.manage.entity.QueryCondition;
import com.hansholdings.manage.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by maozz11347 on 2017/8/2.
 */
@Repository
public class IOUDao {

    @Autowired
    private BaseADDao basedao;

    /**
     * 获取所有的OU，组织结构树使用
     * @return
     */
    public List<OU> getAllOU(String rootOU){return basedao.getAllOU(rootOU);}

    /**
     * 获取树节点下的OU
     * @param query
     * @return
     */
    public List<OU> getOuByQuery(QueryCondition query){return basedao.getOuByQuery(query);}

    /**
     * 获取树节点下的用户
     * @param query
     * @return
     */
    public List<User> getUsers(QueryCondition query){return basedao.getADUsers(query);}

    public  boolean saveOrUpdateOu(OU ou){
        return basedao.saveOrUpdateOu(ou);
    }

    /**
     * 获取树节点下的OU数量
     * @param query
     * @return
     */
    public int getOuSize(QueryCondition query) {return basedao.getOuSize(query);}


    public boolean getOuByQuery(OU query){return basedao.getOuByQuery(query);}

    public List<OU> getOUBydes(String rootOU,String description) {return basedao.getOUBydes(rootOU,description);}


}
