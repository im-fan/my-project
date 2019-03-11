package com.project.service;

import com.project.entity.PageInfo;
import com.project.entity.User;

public interface UserService {

    PageInfo findAll();

    int addUser(User user);
}
