package com.project.controller;

import com.project.entity.PageInfo;
import com.project.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.project.service.UserService;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/")
    @ResponseBody
    String out(){
        return "Hello World";
    }

    @RequestMapping("findAll")
    @ResponseBody
    PageInfo findAll(){

        PageInfo result = userService.findAll();

        return result;
    }

}
