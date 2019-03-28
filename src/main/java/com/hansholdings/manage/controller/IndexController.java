package com.hansholdings.manage.controller;

import com.alibaba.fastjson.JSON;
import com.hansholdings.basic.commons.Constants;
import com.hansholdings.basic.controller.BaseController;
import com.hansholdings.basic.utils.UserUtils;
import com.hansholdings.manage.entity.Logininfo;
import com.hansholdings.manage.service.IFunctionService;
import com.hansholdings.manage.service.ILogininfoService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class IndexController extends BaseController {

    @Autowired
    private IFunctionService functionService;

    @Autowired
    private ILogininfoService logininfoService;

    @RequestMapping({ "", "index" })
    public String index(Model mv) {
        mv.addAttribute("functions", functionService.loadFunctionByAdminId(UserUtils.getCurrentUser().getId()));
        System.out.println(  JSON.toJSONString(functionService.loadFunctionByAdminId(UserUtils.getCurrentUser().getId())).toString());
        return "index";
    }

    /**
     * 退出
     */
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Long adminId= (Long)session.getAttribute(Constants.ADMIN_SESSION_ID);
        Logininfo logininfo=logininfoService.findByAdminId(adminId);
        logininfo.setExitTime(new Date());
        logininfoService.update(logininfo);
        SecurityUtils.getSubject().logout();
        return "redirect:login.html";
    }

    /**
     * 注册
     * 
     * @return
     */
    @RequestMapping("/register")
    public String registerView() {
        return "/authorization/register";
    }

}
