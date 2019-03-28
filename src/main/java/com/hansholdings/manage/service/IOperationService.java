package com.hansholdings.manage.service;

import com.alibaba.fastjson.JSONObject;
import com.hansholdings.basic.service.IBaseService;
import com.hansholdings.manage.entity.Operationinfo;
import com.hansholdings.manage.entity.QueryCondition;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 *
 * @author maozz11347
 * @date 2017/12/19
 */
public interface IOperationService extends IBaseService<Operationinfo, Long> {

    /**
     * 分页查找
     * @param query
     * @return
     */
     Page<Operationinfo> searchByPage(QueryCondition query);

    /**
     * 查找所有
     * @param query
     * @return
     */
     List<Operationinfo> searchAll(QueryCondition query);

    /**
     *根据uuid查找
     * @param uuid
     * @return
     */
     Operationinfo searchByUuid(String uuid);


    JSONObject exportOpLog(QueryCondition query);
}
