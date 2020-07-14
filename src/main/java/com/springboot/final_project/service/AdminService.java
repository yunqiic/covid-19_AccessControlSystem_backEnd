package com.springboot.final_project.service;

import com.springboot.final_project.Entity.Admin;

import java.util.List;
import java.util.Map;


public interface AdminService {
    Map<String,Object> createAdmin(Admin admin);

    Map<String,Object> adminListById(int id,int index, int limit);

    Map<String,Object> adminListByName(String name,int index, int limit);

    Map<String,Object> adminList(int index , int limit);

    int updatePassword(Admin admin);

    int deleteAdmin(Admin admin);


}
