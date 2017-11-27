package com.project.service;

import com.project.entity.PageInfo;
import com.project.entity.User;
import com.project.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

public interface UserService {

    PageInfo findAll();

    int addUser(User user);
}
