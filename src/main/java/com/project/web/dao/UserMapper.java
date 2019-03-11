package com.project.web.dao;

import com.project.web.entity.User;

import java.util.List;

public interface UserMapper{

    List<User> findAll();

    int addUser(User user);
}
