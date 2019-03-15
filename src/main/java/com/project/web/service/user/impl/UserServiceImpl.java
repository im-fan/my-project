package com.project.web.service.user.impl;

import com.alibaba.fastjson.JSONObject;
import com.main.annotation.Pagination;
import com.project.web.entity.PageInfo;
import com.project.web.entity.User;
import com.project.web.dao.UserMapper;
import com.project.web.service.user.UserService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    @Pagination
    public PageInfo findAll() {
        logger.info("=====info====findAll");
        logger.error("=====error=====findAll====");
        int pageSize = 0;
        int pageNumber = 5;
        List<User> result = userMapper.findAll();

        int tot = result.size();

        return new PageInfo(result,tot,pageNumber,pageSize);
    }

    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public User printUser() {
        User user = User.builder()
                .id(10001)
                .name("用户1001")
                .address("杭州")
                .age(25)
                .createTime("2019-03-01")
                .build();
        logger.info("用户信息==>{}", JSONObject.toJSONString(user));
        return user;
    }

    public static void printUserInfo(){
        User user = User.builder()
                .id(10001)
                .name("用户1001")
                .address("杭州")
                .age(25)
                .createTime("2019-03-01")
                .build();
        logger.info("静态方法===用户信息==>{}", JSONObject.toJSONString(user));
    }

}
