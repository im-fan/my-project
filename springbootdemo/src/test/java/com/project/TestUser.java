package com.project;


import com.project.entity.User;
import com.project.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
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

    @Test
    public void findAll(){

        List<User> result= userService.findAll();
        System.out.print(result);
    }

}
