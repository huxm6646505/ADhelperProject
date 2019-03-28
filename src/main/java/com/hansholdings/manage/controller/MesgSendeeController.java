package com.hansholdings.manage.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hansholdings.manage.entity.DatatablesView;
import com.hansholdings.manage.entity.MessgSendee;
import com.hansholdings.manage.entity.QueryCondition;
import com.hansholdings.manage.service.IMesgSendeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by maozz11347 on 2018-9-12.
 */
@Controller
@RequestMapping("/mesgsendee")
public class MesgSendeeController {


    @Autowired
    private IMesgSendeeService mesgSendeeService;

    @RequestMapping({ "", "index" })
    public String loginfoIndex(Model model) {
        return "messgsendee/index";
    }

    /**
     * 通知接收人
     * @return
     */
    @RequestMapping(value="/getMessgSendee",produces = "text/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getMessgSendees(QueryCondition query){
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Pageable pageable = new PageRequest(query.getStart()/query.getLength(),query.getLength(),sort);
        Page<MessgSendee> messgSendees=mesgSendeeService.findAll(pageable);
        DatatablesView<MessgSendee> dataTable = new DatatablesView<>();
        dataTable.setDraw(query.getDraw());
        dataTable.setRecordsFiltered((int) messgSendees.getTotalElements());
        dataTable.setRecordsTotal((int) messgSendees.getTotalElements());
        dataTable.setData(messgSendees.getContent());
        JSONObject data=JSONObject.parseObject(JSON.toJSONString(dataTable));

        System.out.println(data.toString());
        return data;
    }

    @RequestMapping("/saveOrUpdateMessgSendee")
    public String saveOrUpdateOuList(MessgSendee me) throws Exception{
        if(me.getId()!=null){
            mesgSendeeService.update(me);
        }else {
            mesgSendeeService.save(me);
        }
        return "messgsendee/index";
    }
}
