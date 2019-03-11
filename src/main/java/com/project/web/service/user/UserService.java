package com.project.web.service.user;

import com.project.web.entity.PageInfo;
import com.project.web.entity.User;

public interface UserService {

    PageInfo findAll();

    int addUser(User user);
}
