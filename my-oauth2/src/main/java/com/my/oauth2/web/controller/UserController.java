package com.my.oauth2.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.my.oauth2.web.model.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 * @Date 2020/11/8 16:51
 * @Author weiyf
**/
@Api("用户中心")
@RestController
@RequestMapping("/user")
public class UserController {

    @ApiOperation("用户详情")
    @GetMapping("/info")
    public String getUserInfo(){

        UserDTO userDTO = UserDTO.builder()
                .userId(1)
                .age(24)
                .address("杭州市西湖区")
                .userName("咿呀呀")
                .build();
        return JSONObject.toJSONString(userDTO);
    }

    @ApiOperation("新增用户")
    @PostMapping
    public String saveUserInfo(){
        return "添加成功";
    }

}
