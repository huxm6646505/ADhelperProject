package com.hansholdings.manage.controller;

import com.hansholdings.basic.controller.BaseController;
import com.hansholdings.basic.entity.vo.TreeNode;
import com.hansholdings.basic.json.Result;
import com.hansholdings.manage.entity.Function;
import com.hansholdings.manage.service.IFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/function")
public class FunctionController extends BaseController {

    @Autowired
    private IFunctionService functionService;

    @RequestMapping({ "", "index" })
    public String index(Model model) {
        model.addAttribute("functions", functionService.findAll());
        System.out.println(functionService.findAll().size());
        return "function/index";
    }

    @RequestMapping("/loadTreeNode")
    @ResponseBody
    public Result loadTreeNode() {
        List<TreeNode> trArray = functionService.loadTree();
        return new Result(true, "查询成功", trArray);
    }

    @RequestMapping("/add")
    public String addFunction(Function function, Model model) {
        functionService.save(function);
        return "redirect:index.html";
    }

    @RequestMapping("/remove")
    public String remove(Long id, Model model) {
        functionService.delete(id);
        return "redirect:index.html";
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public Result findUserFunctions() {
        return new Result(true, "查询成功", functionService.findAll());
    }
}
