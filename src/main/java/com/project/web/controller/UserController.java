package com.project.web.controller;

import com.project.web.entity.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.project.web.service.user.UserService;

import javax.annotation.Resource;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping
    @ResponseBody
    PageInfo findAll(){

        PageInfo result = userService.findAll();

        return result;
    }

}
