package com.hansholdings.manage.dao;

import com.hansholdings.manage.entity.QueryCondition;
import com.hansholdings.manage.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author maozz11347
 * @date 2017/7/17
 */
@Repository
public class IUserDao{

    @Autowired
    private BaseADDao basedao;

    public List<User> getADUserList(QueryCondition query){return basedao.getADUsers(query);}

    public boolean saveOrUpdateUser(User user){return  basedao.saveOrUpdateUser(user);}

    public boolean deleteUser(String uid){return  basedao.deleteUser(uid);}

    public  boolean  chagePasword(User user){return  basedao.chagePasword(user);}

    public int getADUserSize(QueryCondition query) {return basedao.getADUserSize(query);}

    public List<User> getUserByQuery(User user){return basedao.getUserByQuery(user);}

    public boolean saveUser(User user){return  basedao.saveUser(user);}

    public  boolean updateUser(User user){return  basedao.updateUser(user);}
}
