package com.springboot.final_project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.springboot.final_project.Entity.Admin;
import com.springboot.final_project.mapper.AdminMapper;
import com.springboot.final_project.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public int createAdmin(Admin admin) {
        Integer result = 0;
        QueryWrapper<Admin> wrapper = new QueryWrapper();

        Admin admin1= adminMapper.selectOne(wrapper.eq("username",admin.getUsername()));

        if(admin1 != null){
            result =1;
            return result;
        }else {
//            admin.builder().username(username).password(password).build();
            adminMapper.insert(admin);
            result=0;
            return result;
        }
    }
}
