package com.springboot.final_project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.springboot.final_project.Entity.Admin;
import com.springboot.final_project.Entity.Result;
import com.springboot.final_project.mapper.AdminMapper;
import com.springboot.final_project.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/account")
public class adminController {

    @Autowired
    AdminService adminService;

    @Autowired
    AdminMapper adminMapper;
//(@RequestParam("username") String username,@RequestParam("password") String password,@RequestParam("comfirm_password") String comfirm_password)
    @PostMapping("/admin/create")
    public Result creatAdmin(@RequestBody Map<String, String> map){

        Result result = new Result();
        result.setResult(3);
        if(!Objects.equals(map.get("password"), map.get("comfirm_password"))){
            result.setResult(2);
            return result;
        }else {
            Admin admin = new Admin();
            admin.setUsername(map.get("username"));
            admin.setPassword(map.get("password"));
            System.out.println(admin.toString());
            result.setResult(adminService.createAdmin(admin));;
            return result;
        }




    }
}
