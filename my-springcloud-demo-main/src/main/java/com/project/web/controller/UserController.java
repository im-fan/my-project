package com.project.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.project.web.entity.PageInfo;
import com.project.web.entity.Resp;
import com.project.web.entity.User;
import com.project.web.entity.dto.EditUserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.project.web.service.user.UserService;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
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

    @PostMapping
    private Resp editDesc(@RequestBody List<EditUserDto> list){
        log.info("edit ====>  list={}", JSONObject.toJSONString(list));
        return Resp.success();
    }

    @GetMapping("/static")
    private User findUser(){
        return userService.printUser();
    }

}
