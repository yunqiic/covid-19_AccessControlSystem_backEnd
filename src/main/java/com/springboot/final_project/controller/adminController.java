package com.springboot.final_project.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.final_project.Entity.Admin;
import com.springboot.final_project.Entity.Result;
import com.springboot.final_project.VO.PageVO;
import com.springboot.final_project.mapper.AdminMapper;
import com.springboot.final_project.service.AdminService;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/account")
public class adminController {

    @Autowired
    AdminService adminService;

    @Autowired
    AdminMapper adminMapper;

    //1.按筛选条件获取管理员列表
    @RequestMapping("/admin/list")
    public String adminList(Admin admin,int page,int limit,String sort){
        JSONObject json = new JSONObject();
        json.put("code",20000);
        Map<String, Object> map = new HashMap<String, Object>();
        Page<Admin> pages = new Page<>(page,limit);
        if(admin.getId()!=null&&!admin.getUsername().equals("")){//查id和username
            adminMapper.selectPage(pages,new QueryWrapper<Admin>().like("username",admin.getUsername()).like("id",admin.getId()));
        }else {
            if(admin.getId()!=null) {//只查id
                adminMapper.selectPage(pages,new QueryWrapper<Admin>().like("id",admin.getId()));
            }
            if(!admin.getUsername().equals("")){//只查username
                adminMapper.selectPage(pages,new QueryWrapper<Admin>().like("username",admin.getUsername()));
            }
        }
        if (admin.getId()==null&&admin.getUsername().equals("")){//全列表
            adminMapper.selectPage(pages,new QueryWrapper<Admin>());
        }
        List<Admin> admins = pages.getRecords();
        if(sort.equals("-id")) Collections.reverse(admins);
        map.put("list",admins);
        map.put("total",pages.getTotal());
        json.put("data",map);
        return  json.toJSONString();

    }

    //2.添加管理员
    @PostMapping("/admin/create")
    public String creatAdmin(Admin admin,String confirm_password,String new_password){

        JSONObject json = new JSONObject();
        json.put("code",20000);
        Map<String, Object> map = new HashMap<String, Object>();

        if(!Objects.equals(new_password, confirm_password)){
            map.put("id",0);
            map.put("result",2);//2:两次密码不一致
            json.put("data",map);
            return json.toJSONString();
        }else {
            admin.setPassword(new_password);
            map = adminService.createAdmin(admin);
            json.put("data",map);
            return json.toJSONString();
        }
    }

    //3.修改管理员密码
    @PostMapping("/admin/update")
    public String updateAdmin(String id,String old_password,String new_password,String confirm_password){
        JSONObject json = new JSONObject();
        json.put("code",20000);
        Result result = new Result();
        //信号量  成功:0   两次密码不一致:1   旧密码不正确:2   读写错误:3
        try {
            if (!Objects.equals(new_password, confirm_password)) {
                result.setResult(1);
                json.put("data", result);
                return json.toJSONString();
            } else {
                Admin admin = adminMapper.selectById(id);
                //如果旧密码不正确
                if (!Objects.equals(admin.getPassword(), old_password)) {
                    result.setResult(2);
                    json.put("data", result);
                    return json.toJSONString();
                }
                admin.setPassword(new_password);
                result.setResult(adminService.updatePassword(admin));
                json.put("data", result);
                return json.toJSONString();
            }
        }catch (Exception e) {
            e.printStackTrace();
            result.setResult(3);
            json.put("data", result);
            return json.toJSONString();
        }
    }

    //4.删除管理员
    @PostMapping("/admin/delete")
    public String deleteAdmin(int id){
        JSONObject json = new JSONObject();
        json.put("code",20000);
        Result result = new Result();
        result.setResult(1);
        Admin admin = new Admin();
        admin.setId(id);
        result.setResult(adminService.deleteAdmin(admin));
        json.put("data",result);
        return json.toJSONString();
    }
}
