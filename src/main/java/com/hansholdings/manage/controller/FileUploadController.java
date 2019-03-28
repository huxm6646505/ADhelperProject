package com.hansholdings.manage.controller;

import com.alibaba.fastjson.JSONObject;
import com.hansholdings.manage.entity.QueryCondition;
import com.hansholdings.manage.service.IOperationService;
import com.hansholdings.manage.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by maozz11347 on 2017/10/26.
 */
@Controller
@RequestMapping("/FileUpload")
public class FileUploadController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IOperationService operationService;



    /**
     * 通用文件上传接口，存储到固定地址，以后存储到文件服务器地址
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject uploadFile(@RequestParam(value = "file", required = false) MultipartFile file,String service) throws Exception {
        //TODO dosomething
        JSONObject result;
        String filetype=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
        result= userService.uploadUser(file.getBytes(),filetype,service);
        return result;
    }

    /**
     * 导出用户
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/exportFile", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject exportFile(QueryCondition ou) throws Exception {
        //TODO dosomething
        JSONObject result;
        result=userService.exportUser(ou);
        return result;
    }

    /**
     * 导出操作日志
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/exportOpLog", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject exportOpLog(QueryCondition query) throws Exception {
        //TODO dosomething
        JSONObject result;
        result=operationService.exportOpLog(query);
        return result;
    }


}
