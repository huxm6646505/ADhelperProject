package com.hansholdings.manage.service.impl;


import com.alibaba.fastjson.JSON;
import com.hansholdings.basic.dao.IBaseDao;
import com.hansholdings.basic.entity.User;
import com.hansholdings.basic.log4j.Logger;
import com.hansholdings.basic.service.impl.BaseServiceImpl;
import com.hansholdings.manage.dao.BaseADDao;
import com.hansholdings.manage.dao.IAdminDao;
import com.hansholdings.manage.entity.Admin;
import com.hansholdings.manage.entity.Member;
import com.hansholdings.manage.entity.Role;
import com.hansholdings.manage.service.IAdminService;
import com.hansholdings.manage.service.IRoleService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin, Long> implements IAdminService {

    private static Logger logger = Logger.getLogger(AdminServiceImpl.class);

    @Autowired
    private IAdminDao adminDao;

    @Autowired
    private BaseADDao basedao;

    @Autowired
    private IRoleService roleService;

    @Value("${hansholdings.frame.variables.default-role}")
    private Long          defaultUserRoleId;

    @Override
    public IBaseDao<Admin, Long> getBaseDao() {
        return this.adminDao;
    }

    @Override
    public Admin findByUsername(String username) {
        return adminDao.findByUsername(username);
    }

    @Override
    public void saveOrUpdate(Admin admin) {
        System.out.println(JSON.toJSONString(admin));
        if (admin.getId() != null) {
            Admin adminOne = find(admin.getId());
            User user = adminOne.getUser();
            if (admin.getUser() != null) {
                user.setUsername(admin.getUser().getUsername());
                user.setManager(admin.getUser().getManager());
                if (StringUtils.isNoneBlank(admin.getUser().getPassword())) {
                    String password = new Sha256Hash(admin.getUser().getPassword()).toHex();
                    user.setPassword(password);
                }
            }
            Member member = adminOne.getMember();
            if (member == null) {
                adminOne.setMember(admin.getMember());
            } else {
                if (admin.getMember() != null) {
                    member.setBirthday(admin.getMember().getBirthday());
                    member.setSex(admin.getMember().getSex());
                    member.setName(admin.getMember().getName());
                }
            }
            if(admin.getLocked()!=null){
                adminOne.setLocked(admin.getLocked());
            }
            update(adminOne);
        } else {
            if (admin.getUser() != null) {
                if (StringUtils.isNoneBlank(admin.getUser().getPassword())) {
                    String password = new Sha256Hash(admin.getUser().getPassword()).toHex();
                    admin.getUser().setPassword(password);
                }
            }
            Role role = roleService.find(defaultUserRoleId);
            admin.addToRole(role);
            save(admin);
        }
    }

    @Override
    public Boolean saveAdminRoles(Long id, Long[] roles) {
        try {
            Admin admin = find(id);
            Set<Role> rs = new HashSet<>(roles.length);
            for (Long roleId : roles) {
                Role role = roleService.find(roleId);
                rs.add(role);
            }
            admin.setRoles(rs);
            super.save(admin);
        } catch (Exception e) {
            logger.error("Exception params:[id:%s,roles:%s]", e, id.toString(), roles);
            return false;
        }
        return true;
    }

    @Override
    public Admin findByManager(String manager) {
        return adminDao.findByManager(manager);
    }

    @Override
    public boolean loginValidate(String sAMAccountName, String password) {
        return basedao.loginValidate(sAMAccountName,password);
    }
}
