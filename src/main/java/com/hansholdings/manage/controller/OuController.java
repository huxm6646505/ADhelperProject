package com.hansholdings.manage.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hansholdings.basic.commons.Constants;
import com.hansholdings.basic.json.Result;
import com.hansholdings.basic.ldaywebservice.ISysNotifyTodoWebService;
import com.hansholdings.basic.ldaywebservice.ISysNotifyTodoWebServiceService;
import com.hansholdings.basic.ldaywebservice.NotifyTodoAppResult;
import com.hansholdings.basic.ldaywebservice.NotifyTodoSendContext;
import com.hansholdings.basic.utils.UserUtils;
import com.hansholdings.manage.entity.*;
import com.hansholdings.manage.service.IMesgSendeeService;
import com.hansholdings.manage.service.IOperationService;
import com.hansholdings.manage.service.IOuService;
import com.hansholdings.manage.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author maozz11347
 */
@Controller
@RequestMapping("/ou")
public class OuController {

    @Value("${ad.hansRootOu}")
    private String hansRootOu;

    @Value("${ad.disableRootOu}")
    private String disableRootOu;

    @Autowired
    private IOuService ouService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IOperationService operationService;

    @Autowired
    private IMesgSendeeService mesgSendeeService;

   @RequestMapping({ "", "index" })
    public String index(Model model) {
        return "ou/ouIndex";
    }

    @RequestMapping("/loadOuTree")
    @ResponseBody
    public Result function(Long id, Model model) {
        return new Result(true, "success", ouService.loadTreeAndMarkOUs(hansRootOu));
    }

    @RequestMapping(value="/getUsers",produces = "text/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getUsers(QueryCondition query){
        System.out.println("query"+query.toString());
        List<User> users=ouService.getUsers(query);
        DatatablesView<User> dataTable=new DatatablesView<>();
        dataTable.setDraw(query.getDraw());
        dataTable.setRecordsFiltered(userService.getADUserSize(query));
        dataTable.setRecordsTotal(userService.getADUserSize(query));
        dataTable.setData(users);
      /*  String data = JSON.toJSONString(dataTable);
        System.out.println(data.toString());*/
        JSONObject data=(JSONObject)JSON.toJSON(dataTable);
        System.out.println(data.toString());
        return data;
    }

    @RequestMapping(value="/getOus",produces = "text/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getOus(QueryCondition query){
        System.out.println(query.toString());
        List<OU> ous=ouService.getOuByQuery(query);
        DatatablesView<OU> dataTable=new DatatablesView<>();
        dataTable.setDraw(query.getDraw());
        dataTable.setRecordsFiltered(ouService.getOuSize(query));
        dataTable.setRecordsTotal(ouService.getOuSize(query));
        dataTable.setData(ous);
        JSONObject data=(JSONObject)JSON.toJSON(dataTable);
        System.out.println(data.toString());
        return data;
    }

    @RequestMapping("/saveOrUpdateOu")
    public String saveOrUpdateOu(OU ou) {
        System.out.println(JSON.toJSONString(ou));
        ouService.saveOrUpdateOu(ou);
        return "redirect:index.html";
    }

    @RequestMapping("/saveOrUpdateOuList")
    @ResponseBody
    public boolean saveOrUpdateOuList(OU ou) throws Exception{
        QueryCondition query=new QueryCondition();
        query.setSearch(ou.getUid());
        List<OU> uss= ouService.getOuByQuery(query);
        boolean state=ouService.saveOrUpdateOu(ou);
        String uuid= UUID.randomUUID().toString().replaceAll("-","");
        if(state){
            Operationinfo operationinfo=new Operationinfo();
            operationinfo.setUuid(uuid);
            operationinfo.setUserName(UserUtils.getCurrentUserName());
            operationinfo.setOperationTime(new Date());
            operationinfo.setServiceStype("OU");
            if(ou.getUid()==null||"".equals(ou.getUid())){
                operationinfo.setOperateStype(Constants.OPERATE_STYPE_ADD);
                operationinfo.setCurrentValue(JSON.toJSONString(ou));
            }else {
                operationinfo.setOperateStype(Constants.OPERATE_STYPE_UPDATE);
                operationinfo.setOriginalValue(JSON.toJSONString(uss.get(0)));
                ou.setDistinguishedName("OU=" + ou.getName() + "," + ou.getPid());
                operationinfo.setCurrentValue(JSON.toJSONString(ou));
            }
            operationService.save(operationinfo);
            sendTodo(uuid,ou);

        }
        return state;
    }

    @RequestMapping(value = "/saveOrUpdateUser",method = RequestMethod.POST)
    public String saveOrUpdateUser(User user) {
        System.out.println(JSON.toJSONString(user));
        userService.saveOrUpdateUser(user);
        return "redirect:index.html";
    }

    @RequestMapping("disou")
    public String disOuIndex() {
        return "disou/index";
    }

    @RequestMapping(value="/validOuByQuery",produces = "text/json;charset=UTF-8")
    @ResponseBody
    public Boolean getUsersByquery(OU ou){
        JSONObject data=(JSONObject)JSON.toJSON(ou);
        System.out.println(data.toString());
        return ouService.getOuByQuery(ou);
    }

    @RequestMapping(value="/getOUBydes",produces = "text/json;charset=UTF-8")
    @ResponseBody
    public JSONArray getOUBydes(HttpServletRequest request){
        String rootOU = request.getParameter("rootOU").trim();
        String description = request.getParameter("description").trim();
        System.out.println(rootOU+"\\"+description);
        List<OU> ous=ouService.getOUBydes(rootOU,description);
        JSONArray data=(JSONArray) JSONArray.toJSON(ous);
        System.out.println(data.toString());
        return data;
    }

    @RequestMapping("ouListIndex")
    public String listIndex() {
        return "ou/listIndex";
    }

    public void sendTodo(String uuid,OU ou)  throws Exception {
        ISysNotifyTodoWebService service = new ISysNotifyTodoWebServiceService().getISysNotifyTodoWebServicePort();
        NotifyTodoSendContext context = new NotifyTodoSendContext();
        List<MessgSendee> mes=mesgSendeeService.searchByDeptou(ou.getDistinguishedName());
        StringBuilder messgsendees=new StringBuilder("[");
        for(MessgSendee me:mes){
            messgsendees.append(";"+me.getSendee());
        }
        List<String> messgsendeeList= Arrays.asList(messgsendees.toString().replace("[;","").split(";"));
        StringBuilder targets=new StringBuilder("[");
        for(String str:messgsendeeList){
            targets.append(",{'PersonNo':'"+str+"'}");
        }
        context.setAppName("ADWEB");
        context.setModelName("ADWEB");
        context.setSubject("ADWEB系统OU信息修改调整:"+ou.getName());
        context.setLink("http://ad.hansholdings.com/logging/getAdUpInfo/"+uuid);
        context.setType(2);
        context.setKey("OU");
        context.setModelId(uuid);
        context.setTargets(targets.toString().replace("[,","[")+"]");
        context.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        NotifyTodoAppResult result = service.sendTodo(context);
        if (result != null) {
            if (result.getReturnState() != 2) {
                System.out.println("=============================" + result.getMessage());
            }
        }
    }
}
