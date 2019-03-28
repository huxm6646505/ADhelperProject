package com.hansholdings.manage.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hansholdings.basic.log4j.Logger;
import com.hansholdings.basic.utils.JsonTools;
import com.hansholdings.basic.utils.MyExcelReader;
import com.hansholdings.basic.utils.MyExcelWriter;
import com.hansholdings.manage.dao.IUserDao;
import com.hansholdings.manage.entity.QueryCondition;
import com.hansholdings.manage.entity.User;
import com.hansholdings.manage.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by maozz11347 on 2017/7/17.
 */

@Service
public class UserServiceImpl  implements IUserService{

    private static Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private IUserDao userDao;

    @Autowired
    private Environment env;

    @Override
    public List<User> getAllUser(QueryCondition query) {
        return userDao.getADUserList(query);
    }

    @Override
    public boolean saveOrUpdateUser(User user) {
        return userDao.saveOrUpdateUser(user);
    }

    @Override
    public boolean deleteUser(String uid) {
        return userDao.deleteUser(uid);
    }

    @Override
    public boolean chagePasword(User user) { return userDao.chagePasword(user);}

    @Override
    public int getADUserSize(QueryCondition query) {
        return userDao.getADUserSize(query);
    }

    @Override
    public List<User> getUserByQuery(User user) {
        return userDao.getUserByQuery(user);
    }

    @Override
    public JSONObject uploadUser(byte[] bytes,String filetype,String service){
        JSONArray faildata=new JSONArray();
        JSONObject result;
        MyExcelReader reader = new MyExcelReader();
        List keys = Arrays.asList(reader.getRowName(env.getProperty(service)));
        JSONArray jsons = null;
        try {
            jsons = reader.read2JSONObject(keys, bytes,filetype,new Integer[]{0},null);

        User nuser=null;
        for(int i=0;i<jsons.size();i++) {
            JSONObject jsaryobj = (JSONObject) jsons.get(i);
            Set<String> set = jsaryobj.keySet();
            Boolean isBlank=false;
            for(String key : set){
                boolean isNotNUll=false;
               if("updateUserUpload".equals(service)&&"distinguishedName".equals(key)){
                   isNotNUll=(jsaryobj.get(key) == null||"".equals(jsaryobj.get(key)));
               }else if(!"updateUserUpload".equals(service)){
                   isNotNUll=!"physicalDeliveryOfficeName".equals(key)&&(jsaryobj.get(key) == null||"".equals(jsaryobj.get(key)));
               }
                if (isNotNUll) {
                    jsaryobj.put("error",  key + "不能为空！");
                    faildata.add(jsaryobj);
                    isBlank=true;
                    break;
                }
            }
            if(!isBlank){
                if("adUserUpload".equals(service)){
                        User user=new User();
                        user.setsAMAccountName((String)jsaryobj.get("sAMAccountName"));
                        if(userDao.getUserByQuery(user).size()>0){
                            jsaryobj.put("error", "sAMAccountName已存在！");
                            faildata.add(jsaryobj);
                            continue;
                        }
                        user.setTelephoneNumber((String)jsaryobj.get("telephoneNumber"));
                        user.setsAMAccountName(null);
                        if(userDao.getUserByQuery(user).size()>0){
                            jsaryobj.put("error", "telephoneNumber已存在！");
                            faildata.add(jsaryobj);
                            continue;
                        }
                        user.setDistinguishedName((String)jsaryobj.get("distinguishedName"));
                        user.setsAMAccountName(null);
                        user.setTelephoneNumber(null);
                        if(userDao.getUserByQuery(user).size()>0){
                            jsaryobj.put("error", "distinguishedName已存在！");
                            faildata.add(jsaryobj);
                            continue;
                        }
                        user.setUserPrincipalName((String)jsaryobj.get("userPrincipalName"));
                        user.setDistinguishedName(null);
                        user.setTelephoneNumber(null);
                        if(userDao.getUserByQuery(user).size()>0){
                             jsaryobj.put("error", "userPrincipalName已存在！");
                             faildata.add(jsaryobj);
                             continue;
                        }
                        nuser = (User)JSONObject.parseObject(jsaryobj.toJSONString(),User.class);
                        if(!userDao.saveUser(nuser)){
                            jsaryobj.put("error", "服务端异常！");
                            faildata.add(jsaryobj);
                            continue;
                        }
                }else {
                    User user = new User();
                    user.setDistinguishedName((String) jsaryobj.get("distinguishedName"));
                    if (userDao.getUserByQuery(user).size() == 0) {
                        jsaryobj.put("error", "distinguishedName不存在！");
                        faildata.add(jsaryobj);
                        continue;
                    }
                    if("updateUserUpload".equals(service)) {
                       if(jsaryobj.get("telephoneNumber")!=null&&!"".equals((String) jsaryobj.get("telephoneNumber"))) {
                           user.setTelephoneNumber((String) jsaryobj.get("telephoneNumber"));
                           int sa = userDao.getUserByQuery(user).size();
                           user.setDistinguishedName(null);
                           int sb = userDao.getUserByQuery(user).size();
                           if (sa == 0 && sb > 0) {
                               jsaryobj.put("error", "telephoneNumber已存在！");
                               faildata.add(jsaryobj);
                               continue;
                           }
                       }
                    }
                    nuser = (User) JSONObject.parseObject(jsaryobj.toJSONString(), User.class);
                    if (!userDao.updateUser(nuser)) {
                        jsaryobj.put("error", "服务端异常！");
                        faildata.add(jsaryobj);
                        continue;
                    }
                }
            }
        }
        if(faildata.size()>0){
            MyExcelWriter writer =new MyExcelWriter();
            String postad = UUID.randomUUID().toString().replaceAll("-", "");
            String filepathString =System.getProperty("user.dir").replace("\\","/").replace("bin","webapps/ROOT/WEB-INF/classes/static")+"/exportFile/"+postad+".xls";
           // String filepathString ="D:\\Workspace\\IntellijIDEA\\adusermanage\\src\\main\\resources\\static\\exportFile\\"+postad+".xls";
            System.out.println(filepathString);
            try {
                writer.write2Excel(Arrays.asList(reader.getRowName(env.getProperty(service)+",error")), faildata, filepathString);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                logger.error("import_export Exception",e,faildata);
                e.printStackTrace();
            }
            result=JSON.parseObject("{code:'501',desp:'共有"+jsons.size()+"条数据，其中有"+faildata.size()+"条失败数据',exportFnm:'"+postad+".xls'}");
        }else{
            result=JSON.parseObject("{code:'200',desp:'数据全部上传成功,共有"+jsons.size()+"条数据'}");
        }
        System.out.println(JSON.toJSONString("========="+faildata));
        } catch (Exception e) {
            e.printStackTrace();
            result= JsonTools.getFailtResult(e.getMessage());
        }
        return result;
    }

    @Override
    public JSONObject exportUser(QueryCondition query){
            JSONObject result;
             List<User> users=userDao.getADUserList(query);
            MyExcelWriter writer =new MyExcelWriter();
            String postad = UUID.randomUUID().toString().replaceAll("-", "");
             String filepathString =System.getProperty("user.dir").replace("\\","/").replace("bin","webapps/ROOT/WEB-INF/classes/static")+"/exportFile/"+postad+".xls";
            //String filepathString ="D:\\Workspace\\IntellijIDEA\\adusermanage\\src\\main\\resources\\static\\exportFile\\"+postad+".xls";
          //  System.out.println(filepathString);
            try {
                writer.write2Excel(Arrays.asList(env.getProperty("userobject").toString().split(",")), JSONArray.parseArray(JSON.toJSONString(users)), filepathString);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                logger.error("export Exception",e,users);
                e.printStackTrace();

            }
            result=JSON.parseObject("{code:'200',desp:'共有"+users.size()+"条数据，',exportFnm:'"+postad+".xls'}");

        return result;
    }

}
