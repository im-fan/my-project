package com.project;

import com.project.web.service.user.UserService;
import com.project.web.service.user.impl.UserServiceImpl;
import junit.framework.TestCase;

public class TestDemo extends TestCase {

    /** 鼠标右键直接运行 **/
    public void testPrintUser(){
        UserService userService = new UserServiceImpl();
        userService.printUser();
    }

}
