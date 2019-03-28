package com.hansholdings.manage.controller;

import com.alibaba.fastjson.JSON;
import com.hansholdings.manage.entity.DatatablesView;
import com.hansholdings.manage.entity.Position;
import com.hansholdings.manage.entity.QueryCondition;
import com.hansholdings.manage.entity.User;
import com.hansholdings.manage.service.IPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by maozz11347 on 2017/8/18.
 */
@Controller
@RequestMapping("/position")
public class PositionController {

    @Autowired
    private IPositionService positionService;

    @RequestMapping({ "", "index" })
    public String index(Model model) {
        return "position/index";
    }

    @RequestMapping(value="/getPositions",produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getUsers(HttpServletRequest request){
        List<Position> pos=positionService.getPositions();
        DatatablesView<Position> dataTable=new DatatablesView<>();
        dataTable.setData(pos);
        String data = JSON.toJSONString(dataTable);
        System.out.println(data.toString());
        return data;
    }

    @RequestMapping(value="/getMembers/{uid}",produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String getMembers (@PathVariable("uid") String uid){
        System.out.println("uid:"+uid);
        List<User> pos=positionService.getMembersByPos(uid);
        DatatablesView<User> dataTable=new DatatablesView<>();
        dataTable.setData(pos);
        String data = JSON.toJSONString(dataTable);
        System.out.println(data.toString());
        return data;
    }

    @RequestMapping("/saveOrUpdate")
    public String saveOrUpdate1(Position pos) {
        System.out.println(JSON.toJSONString(pos));
        System.out.println( positionService.saveOrUpdatePosition(pos));
        return "redirect:index.html";
    }
}
