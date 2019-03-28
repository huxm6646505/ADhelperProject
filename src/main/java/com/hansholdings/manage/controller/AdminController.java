package com.hansholdings.manage.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hansholdings.basic.commons.Constants;
import com.hansholdings.basic.controller.BaseController;
import com.hansholdings.basic.entity.User;
import com.hansholdings.manage.entity.Admin;
import com.hansholdings.manage.entity.Ourole;
import com.hansholdings.manage.service.IAdminService;
import com.hansholdings.manage.service.IOuroleService;
import com.hansholdings.manage.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Set;


@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private IRoleService  roleService;

    @Autowired
    private IOuroleService ouroleService;



   @RequestMapping({ "", "index" })
    public String index(Model model) {
        model.addAttribute("admins", adminService.findAll());
        return "admin/index";
    }

    @RequestMapping("/remove")
    public String remove(Admin admin) {
        Integer locked=admin.getLocked()==1?0:1;
        admin.setLocked(locked);
        adminService.saveOrUpdate(admin);
        return "redirect:index.html";
    }

    @RequestMapping("/oauth")
    public String oauth(Long id, Model model) {
        model.addAttribute("admin", adminService.find(id));
        model.addAttribute("roles", roleService.findAll());
        return "authorization/userIndex";
    }

    @RequestMapping(value="/ourole",produces = "text/json;charset=UTF-8")
    @ResponseBody
    public JSONArray ourole(Long id) {
        System.out.printf(id.toString());
        JSONArray data=JSONArray.parseArray(JSONArray.toJSONString(adminService.find(id).getOuroles()));
        System.out.println(data.toJSONString());

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        System.out.println("-------------------"+((User)session.getAttribute(Constants.USER_SESSION_ID)).getManager());
        Set<Ourole> s= (Set<Ourole>)session.getAttribute(Constants.OUROLES_SESSION_ID);
        for (Ourole ourole :s){
            System.out.println(ourole.getOudn());
        }
        return data;
    }

    @RequestMapping(value="/ourolesubmit")
    @ResponseBody
    public Boolean ourolesubmit(Ourole ourole) {
        System.out.println("======="+JSON.toJSONString(ourole));
        return  ouroleService.saveOrUpdate(ourole);
    }

    @RequestMapping(value="/ouroledelete",produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String ouroledelete(Long id) {
        System.out.println("======="+id.toString());
        ouroleService.delete(id);
        return JSON.toJSONString("{\"msg\":\"true\"}");
    }

    @RequestMapping("/oauthsubmit")
    public String oauthsubmit(Long id, Long[] roleIds) {
        adminService.saveAdminRoles(id, roleIds);
        return "redirect:index.html";
    }

    @RequestMapping("/saveOrUpdate")
    public String saveOrUpdate(Admin admin) {
        adminService.saveOrUpdate(admin);
        return "redirect:index.html";
    }
}
