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

    User getByUserName(String username);

    User getByOpenid(String openid);

    String ChangePwd(int id,String old_password,String password,String confirm_password);

    String CancelBind(int id);

    String Bind(int id,String openid);

    String IfBind(int id);

    String Edit(int id,int sex,String identity_card,String house_no,String photo);

}
