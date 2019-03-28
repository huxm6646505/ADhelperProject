package com.hansholdings.manage.controller;

import com.hansholdings.basic.log4j.Logger;
import com.hansholdings.manage.entity.Admin;
import com.hansholdings.manage.entity.Logininfo;
import com.hansholdings.manage.service.IAdminService;
import com.hansholdings.manage.service.ILogininfoService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RequestMapping("/login")
@Controller
public class LoginController {

    private static Logger logger = Logger.getLogger(LoginController.class);
    @Autowired
    private IAdminService adminService;

    @Autowired
    private ILogininfoService logininfoService;

    @Value("${ad.landaryUsername}")
    private String landaryUsername;

    @Value("${ad.landaryPassword}")
    private String landaryPassword;

    @RequestMapping({ "", "index" })
    public String loginView() {
        return "/authorization/login";
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String submit(String username, String password,HttpServletRequest request) {
        try {
            Admin admin=adminService.findByUsername(username);
            if(adminService.loginValidate(username, password)){
                password=admin.getUser().getPassword();
            }else {
                // sha256加密
                password = new Sha256Hash(password).toHex();
            }
            Subject subject = SecurityUtils.getSubject();

            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            subject.login(token);

            saveLoginRecord(username,admin,request);
        } catch (Exception e) {
            logger.error("用户登录",e);
            return "redirect:index.html";
        } /*catch (IncorrectCredentialsException e) {
            return "redirect:index.html";
        } catch (LockedAccountException e) {
            logger.error("登录",e);
            return "redirect:index.html";
        } catch (AuthenticationException e) {
            return "redirect:index.html";
        }*/

        return "redirect:/index.html";
    }

    /**
     * 蓝凌登录接口
     * @param username
     * @param password
     * @param sAMAccountName
     * @return
     */
    @RequestMapping(value = "/single/{username}/{password}/{sAMAccountName}", method = RequestMethod.GET)
    public String submit1(@PathVariable  String username,@PathVariable  String password,@PathVariable  String sAMAccountName,HttpServletRequest request) {
        if (!landaryUsername.equals(username) || !landaryPassword.equals(password)) {
            return "redirect:/index.html";
        }
        try {
            Subject subject = SecurityUtils.getSubject();
            Admin admin=adminService.findByUsername(sAMAccountName);
            if(admin==null){
                return "redirect:/index.html";
            }else {
                UsernamePasswordToken token = new UsernamePasswordToken(admin.getUser().getUsername(), admin.getUser().getPassword());
                subject.login(token);
                saveLoginRecord(username,admin,request);
                System.out.println("login");
            }
        } catch (Exception e) {
            logger.error("OA登录",e);
            e.printStackTrace();
        }/* catch (IncorrectCredentialsException e) {
            e.printStackTrace();
        } catch (LockedAccountException e) {
            e.printStackTrace();
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }*/

        return "redirect:/index.html";
    }

    public void saveLoginRecord(String username,Admin admin,HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        Logininfo logininfo=new Logininfo();
        logininfo.setUserName(username);
        logininfo.setIpAddre(ip);
        logininfo.setLoginTime(new Date());
        logininfo.setBrowerVersion(request.getHeader("user-agent"));
        logininfo.setAdminId(admin.getId());
        logininfoService.save(logininfo);
    }
}
