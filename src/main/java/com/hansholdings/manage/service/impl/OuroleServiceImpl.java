package com.hansholdings.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.hansholdings.basic.dao.IBaseDao;
import com.hansholdings.basic.log4j.Logger;
import com.hansholdings.basic.service.impl.BaseServiceImpl;
import com.hansholdings.manage.dao.IOuroleDao;
import com.hansholdings.manage.entity.Ourole;
import com.hansholdings.manage.service.IOuroleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by maozz11347 on 2017/11/10.
 */
@Service
public class OuroleServiceImpl extends BaseServiceImpl<Ourole, Long>  implements IOuroleService {
    private static Logger logger = Logger.getLogger(RoleServiceImpl.class);

    @Autowired
    private IOuroleDao ouroleDao;

    @Override
    public Boolean saveOrUpdate(Ourole ourole) {
        System.out.println(JSON.toJSONString(ourole));
        if (ourole.getId() != null) {
            Ourole adminOne = find(ourole.getId());
                if (StringUtils.isNoneBlank(ourole.getOudn())) {
                    adminOne.setOudn(ourole.getOudn());
            }
            update(adminOne);
        } else {
            save(ourole);
        }
        return true;
    }

    @Override
    public IBaseDao<Ourole, Long> getBaseDao()  {
        return this.ouroleDao;
    }
}
