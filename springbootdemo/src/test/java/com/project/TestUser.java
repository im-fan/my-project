package com.project;


import com.project.entity.PageInfo;
import com.project.entity.User;
import com.project.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
//@SpringApplicationConfiguration(classes = StartController.class)// 1.4.0 前版本
public class TestUser {

    @Autowired
    public UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(TestUser.class);

    @Test
    public void findAll(){
        PageInfo result= userService.findAll();
        System.out.print(result);
    }

    @Test
    public void addUser(){
        User user = new User();
        user.setName("ccc");
        user.setAge(24);
        user.setAddress("地球");
        int id = userService.addUser(user);
        System.out.print(id);
    }

}
