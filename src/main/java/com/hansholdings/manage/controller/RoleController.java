package com.hansholdings.manage.controller;

import com.hansholdings.basic.json.Result;
import com.hansholdings.manage.entity.Role;
import com.hansholdings.manage.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @RequestMapping({ "", "index" })
    public String index(Model model) {
        model.addAttribute("roles", roleService.findAll());
        return "role/index";
    }

    @RequestMapping("/remove")
    public String remove(Long id) {
        roleService.delete(id);
        return "redirect:index.html";
    }

    @RequestMapping("/oauth")
    public String oauth(Long id, Model model) {
        model.addAttribute("role", roleService.find(id));
        return "authorization/roleIndex";
    }

    @RequestMapping("/loadFunctionTree")
    @ResponseBody
    public Result function(Long id, Model model) {
        return new Result(true, "success", roleService.loadTreeAndMarkRoleFunctions(id));
    }

    @RequestMapping("/oauthsubmit")
    @ResponseBody
    public Result oauthsubmit(@RequestBody Role role) {
        return new Result(roleService.saveRoleFunctions(role.getId(), role.getFunctions()), "success", null);
    }

    @RequestMapping("/saveOrUpdate")
    public String saveOrUpdate(Role role) {
        roleService.saveOrUpdate(role);
        return "redirect:index.html";
    }
}
