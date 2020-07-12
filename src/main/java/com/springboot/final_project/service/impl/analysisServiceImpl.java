package com.springboot.final_project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.springboot.final_project.Entity.User;
import com.springboot.final_project.mapper.UserMapper;
import com.springboot.final_project.service.analysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class analysisServiceImpl implements analysisService {

    @Autowired
    UserMapper userMapper;

    @Override
    public List<Integer> analysisHealth() {
        int health_count = userMapper.selectCount(new QueryWrapper<User>().eq("health_status",0));
        int unhealth_count = userMapper.selectCount(new QueryWrapper<User>().ge("health_status",1));
        List<Integer> list = new ArrayList<Integer>();
        list.add(health_count);
        list.add(unhealth_count);
        return list;
    }
}
