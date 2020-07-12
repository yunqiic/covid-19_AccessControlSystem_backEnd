package com.springboot.final_project.service;

import com.springboot.final_project.Entity.User;

import java.util.Map;

public interface UserService {

    int add(User user);

    Map<String,Object> getList(User user, int index, int limi, String sort);

    void delete(int id);

    void lock(int id);

    void unLock(int id);

    User getById(int id);

}
