package com.hansholdings.manage.controller;

import com.alibaba.fastjson.JSONObject;
import com.hansholdings.manage.service.ILdapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/ldap")
public class LdapController {


    @Autowired
    private ILdapService ldapService;


   @RequestMapping({ "", "index" })
   public String index(Model model) {
       model.addAttribute("ldap", ldapService.getLdapInfo());
       return "ou/ldapSetting";
   }

    @RequestMapping("/saveOrUpdateLdap")
    public String saveOrUpdateOu(HttpServletRequest request) {
       JSONObject ldap=new JSONObject();
        ldap.put("username", request.getParameter("username"));
        ldap.put("password",request.getParameter("password"));
        ldap.put("ldapURL",request.getParameter("ldapURL"));
        System.out.println(ldap.toJSONString());
        System.out.println(ldapService.saveOrUpdateLdap(ldap));
       // System.out.println(request.getParameter("username"));
        return "forward:/ldap";
    }

}
