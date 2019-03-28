package com.hansholdings.manage.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hansholdings.manage.entity.DatatablesView;
import com.hansholdings.manage.entity.Logininfo;
import com.hansholdings.manage.entity.Operationinfo;
import com.hansholdings.manage.entity.QueryCondition;
import com.hansholdings.manage.service.ILogininfoService;
import com.hansholdings.manage.service.IOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author maozz11347
 * @date 2017/12/19
 */
@Controller
@RequestMapping("/logging")
public class LoggingController {

    @Autowired
    private ILogininfoService logininfoService;

    @Autowired
    private IOperationService operationService;

    @RequestMapping( "/loginfo" )
    public String loginfoIndex(Model model) {
        return "logging/loginfo";
    }

    /**
     * 登录日志
     * @return
     */
    @RequestMapping(value="/getLogininfos",produces = "text/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getLogininfos(QueryCondition query){
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Pageable pageable = new PageRequest(query.getStart()/query.getLength(),query.getLength(),sort);
        Page<Logininfo> logininfos=logininfoService.findAll(pageable);
        DatatablesView<Logininfo> dataTable=new DatatablesView<>();
        dataTable.setDraw(query.getDraw());
        dataTable.setRecordsFiltered((int)logininfos.getTotalElements());
        dataTable.setRecordsTotal((int)logininfos.getTotalElements());
        dataTable.setData(logininfos.getContent());
        JSONObject data=JSONObject.parseObject(JSON.toJSONString(dataTable));
        return data;
    }

    @RequestMapping( "/operatiorninfo" )
    public String operationIndex(){
        return "logging/operationinfo";
    }

    /**
     * 操作日志
     * @param query
     * @return
     */
    @RequestMapping(value="/getOperationinfos",produces = "text/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getUsers(QueryCondition query){
        Page<Operationinfo> operationinfos=operationService.searchByPage(query);
        DatatablesView<Operationinfo> dataTable=new DatatablesView<>();
        dataTable.setDraw(query.getDraw());
        dataTable.setRecordsFiltered((int)operationinfos.getTotalElements());
        dataTable.setRecordsTotal((int)operationinfos.getTotalElements());
        dataTable.setData(operationinfos.getContent());
        JSONObject data=JSONObject.parseObject(JSON.toJSONString(dataTable));
        return data;
    }

    @RequestMapping(value="/getAdUpInfo/{uuid}",produces = "text/json;charset=UTF-8")
    public String viewindex(@PathVariable String uuid,Model model) {
        model.addAttribute("adupinfo", operationService.searchByUuid(uuid));
        return "logging/adupinfo";
    }
}

