package com.hansholdings.manage.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hansholdings.basic.commons.Constants;
import com.hansholdings.basic.controller.BaseController;
import com.hansholdings.basic.utils.UserUtils;
import com.hansholdings.manage.entity.DatatablesView;
import com.hansholdings.manage.entity.Operationinfo;
import com.hansholdings.manage.entity.QueryCondition;
import com.hansholdings.manage.entity.User;
import com.hansholdings.manage.service.IFunctionService;
import com.hansholdings.manage.service.IOperationService;
import com.hansholdings.manage.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * @author maozz11347
 */
@Controller
@RequestMapping("/aduser")
public class AdUserController extends BaseController {
    @Autowired
    private IUserService userService;

    @Autowired
    private IOperationService operationService;

    @Autowired
    private IFunctionService functionService;

    @RequestMapping( "index" )
    public String index(Model model) {
        model.addAttribute("users", userService.getAllUser(null));
        String functionRole=JSON.toJSONString(functionService.loadFunctionRoleByAdminId(UserUtils.getCurrentUser().getId()));
        model.addAttribute("updateUserInfo", functionRole.contains("updateUserInfo"));
        model.addAttribute("passwUpdate", functionRole.contains("passwUpdate"));
        model.addAttribute("accountState", functionRole.contains("accountState"));
        model.addAttribute("userMagTool", functionRole.contains("userMagTool"));
        return "aduser/index";
    }

    @RequestMapping(value="/getUsers",produces = "text/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getUsers(QueryCondition query){
        System.out.println("query"+query.toString());
        List<User> users=userService.getAllUser(query);
        DatatablesView<User> dataTable=new DatatablesView<>();
        dataTable.setDraw(query.getDraw());
        dataTable.setRecordsFiltered(userService.getADUserSize(query));
        dataTable.setRecordsTotal(userService.getADUserSize(query));
        dataTable.setData(users);
       // JSONObject data=(JSONObject)JSON.toJSON(dataTable);
        JSONObject data=JSONObject.parseObject(JSON.toJSONString(dataTable));
        return data;
    }

    @RequestMapping(value="/validUserByQuery",produces = "text/json;charset=UTF-8")
    @ResponseBody
    public Boolean getUsersByquery(User user) {
        Boolean isUsed=false;
        JSONObject data=(JSONObject)JSON.toJSON(user);
        System.out.println(data.toString());
        if(userService.getUserByQuery(user).size()>0){
            isUsed=true;
        }
        return isUsed;
    }

    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public Boolean saveOrUpdate(User user) {
        System.out.println(JSON.toJSONString(user));
        User us=new User();
        us.setsAMAccountName(user.getsAMAccountName());
        List<User> uss= userService.getUserByQuery(us);
        boolean state=userService.saveOrUpdateUser(user);
        if(state){
            Operationinfo operationinfo=new Operationinfo();
            operationinfo.setUserName(UserUtils.getCurrentUserName());
            operationinfo.setOperationTime(new Date());
            operationinfo.setServiceStype("ADUSER");
            if(user.getDistinguishedName()==null){
                operationinfo.setOperateStype(Constants.OPERATE_STYPE_ADD);
                operationinfo.setCurrentValue(JSON.toJSONString(user));
            }else {
                operationinfo.setOperateStype(Constants.OPERATE_STYPE_UPDATE);
                operationinfo.setOriginalValue(JSON.toJSONString(uss.get(0)));
                user.setDistinguishedName("CN=" + user.getName() + "," + user.getDepartment());
                operationinfo.setCurrentValue(JSON.toJSONString(user));
            }
            operationService.save(operationinfo);
        }
        return state;
    }

    @RequestMapping("/upPwdUser")
    public String upPwdUser(User user) {
        boolean state=userService.chagePasword(user);
        if(state){
            Operationinfo operationinfo=new Operationinfo();
            operationinfo.setUserName(UserUtils.getCurrentUserName());
            operationinfo.setOperationTime(new Date());
            operationinfo.setServiceStype("ADUSER");
            operationinfo.setOperateStype(Constants.OPERATE_STYPE_UPDTEASSWORD);
            operationinfo.setCurrentValue(JSON.toJSONString(user));
            operationService.save(operationinfo);
        }
        return "redirect:index.html";
    }

    @RequestMapping("/deleteUser/{uid}")
    public String deleteUser(@PathVariable("uid") String uid) {
        System.out.println("uid="+JSON.toJSONString(uid));
        System.out.println( userService.deleteUser(uid));
        return "redirect:index.html";
    }
}
