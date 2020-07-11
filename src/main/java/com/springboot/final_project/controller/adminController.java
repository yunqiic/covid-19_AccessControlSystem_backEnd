package com.springboot.final_project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.springboot.final_project.Entity.Admin;
import com.springboot.final_project.mapper.AdminMapper;
import com.springboot.final_project.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/account")
public class adminController {

    @Autowired
    AdminService adminService;

    @Autowired
    AdminMapper adminMapper;

    @PostMapping("/admin/create")
    public int creatAdmin(String username,String password,String comfirm_password){

        Integer result = 3;
        if(!Objects.equals(password, comfirm_password)){
            result =2;
            return result;
        }else {
            Admin admin = new Admin();
            admin.setUsername(username);
            admin.setPassword(password);
            result=adminService.createAdmin(admin);
            return result;
        }




    }
}
