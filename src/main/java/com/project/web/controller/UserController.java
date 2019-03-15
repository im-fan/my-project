package com.project.web.controller;

import com.project.web.entity.PageInfo;
import com.project.web.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.project.web.service.user.UserService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping
    private PageInfo findAll(){

        PageInfo result = userService.findAll();

        return result;
    }

    @GetMapping("/static")
    private User findUser(){
        return userService.printUser();
    }

}
