package com.project.dao;

import com.project.entity.User;

import java.util.List;

public interface UserMapper{

    List<User> findAll();

    int addUser(User user);
}
