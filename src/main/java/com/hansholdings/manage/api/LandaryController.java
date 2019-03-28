package com.hansholdings.manage.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hansholdings.basic.commons.Constants;
import com.hansholdings.basic.utils.JsonTools;
import com.hansholdings.manage.dao.BaseADDao;
import com.hansholdings.manage.entity.Operationinfo;
import com.hansholdings.manage.entity.RestMessage;
import com.hansholdings.manage.entity.User;
import com.hansholdings.manage.service.IOperationService;
import com.hansholdings.manage.service.IUserService;
import com.hansholdings.manage.service.LandaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by maozz11347 on 2017/8/3.
 */
@Api(value="/landary", tags="蓝凌接口模块")
@RestController
@RequestMapping("/landary")
public class LandaryController {
    @Autowired
    private BaseADDao baseADDao;

    @Value("${ad.landaryUsername}")
    private String landaryUsername;

    @Value("${ad.landaryPassword}")
    private String landaryPassword;

    @Autowired
    private LandaryService landaryService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IOperationService operationService;

    @ApiOperation(value = "接口服务", notes = "入职、离职、调职、信息更新")
/*    @ApiImplicitParams({
            @ApiImplicitParam(name="username",value="用户名",required = true ,dataType = "String"),
            @ApiImplicitParam(name="password",value="密码",required = true ,dataType = "String"),
            @ApiImplicitParam(name="service",value="服务名",required = true ,dataType = "String"),
    })*/
    @PostMapping("/ad")
    public RestMessage addUser(@RequestBody JSONObject requestJson) {
        JSONObject json ;
        if (!requestJson.containsKey("username") || !requestJson.containsKey("password") ||
                !landaryUsername.equals(requestJson.get("username")) || !landaryPassword.equals(requestJson.get("password"))) {
            json = JsonTools.getFailtResult(Constants.AUTHEN_ERROR, "账号密码不正确！");
        } else {
            switch (requestJson.getString("service")) {
                case "leave":
                    json = landaryService.updateUserState(requestJson);
                    break;
                case "entry":
                    json = landaryService.addUser(requestJson);
                    break;
                case "transfer":
                    json = landaryService.updateUserOU(requestJson);
                    break;
                case "update":
                    json = landaryService.updateUserInfo(requestJson);
                    break;
                default:
                    json = JsonTools.getFailtResult(Constants.METHOD_REQUEST_ERROR, "无法找到该服务方法！");
                    break;
            }
            if("200".equals(json.getString("code"))) {
                operationService.save(getOperationinfo(requestJson));
            }
        }
        System.out.println(JSON.toJSONString(requestJson.toString()));
        System.out.println(JSON.toJSONString(json.toString()));
        return JSON.parseObject(json.toJSONString(),RestMessage.class);
    }

    public Operationinfo getOperationinfo(JSONObject requestJson){
        Operationinfo operationinfo=new Operationinfo();
        operationinfo.setUserName("Landray");
        operationinfo.setOperationTime(new Date());
        operationinfo.setServiceStype("LANDRAYINTERFACE");
        operationinfo.setOperateStype(OperateMap().get(requestJson.getString("service")).toString());
        requestJson.remove("username");
        requestJson.remove("password");
        operationinfo.setCurrentValue(requestJson.toJSONString());
        User us=new User();
        String distinguishedName;
        if(!"transfer".equals(requestJson.get("service").toString())){
            distinguishedName = "CN=" + requestJson.get("givenName").toString() + ",OU=" + JsonTools.ouToString(requestJson.get("distinguishedName").toString()) + ",DC=hansholdings,DC=com" ;
            us.setDistinguishedName(distinguishedName);
            List<User> uss= userService.getUserByQuery(us);
            operationinfo.setOriginalValue(JSON.toJSONString(uss.get(0)));
        }
        return operationinfo;
}


    public Map<String, String> OperateMap(){
        Map<String, String> map = new HashMap<String, String>(16);
        map.put("entry",Constants.OPERATE_STYPE_ENTRY);
        map.put("leave",Constants.OPERATE_STYPE_LEAVE);
        map.put("transfer",Constants.OPERATE_STYPE_TRANSFER);
        map.put("update",Constants.OPERATE_STYPE_UPDATES);
        return map;
    }
}
