package com.springboot.final_project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.final_project.Entity.Admin;
import com.springboot.final_project.mapper.AdminMapper;
import com.springboot.final_project.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
            adminMapper.insert(admin);
            result=0;
            return result;
        }
    }

    @Override
    public Map<String,Object> adminListById(int id,int index, int limit) {
        Page<Admin> page = new Page<>(index,limit);
        adminMapper.selectPage(page,new QueryWrapper<Admin>().eq("id",id));
        List<Admin> admins = page.getRecords();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("list",admins);
        map.put("total",page.getTotal());

        return map;
    }

    @Override
    public Map<String,Object> adminListByName(String name,int index, int limit) {
        Page<Admin> page = new Page<>(index,limit);
        adminMapper.selectPage(page,new QueryWrapper<Admin>().like("username",name));
        List<Admin> admins = page.getRecords();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("list",admins);
        map.put("total",page.getTotal());
        return map;
    }

    @Override
    public Map<String,Object> adminList(int index, int limit) {

        Page<Admin> page = new Page<>(index,limit);
        adminMapper.selectPage(page,null);
        List<Admin> admins = page.getRecords();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("list",admins);
        map.put("total",page.getTotal());
        return  map;


    }

    @Override
    public int updatePassword(Admin admin) {
        Integer result = 1;
        adminMapper.updateById(admin);
        result=0;
        return result;
    }

    @Override
    public int deleteAdmin(Admin admin){
        Integer result = 0;
        adminMapper.deleteById(admin.getId());
        return result;
    }
}
