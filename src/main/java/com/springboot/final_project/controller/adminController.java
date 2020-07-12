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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping("/account")
public class adminController {

    @Autowired
    AdminService adminService;

    @Autowired
    AdminMapper adminMapper;

    @GetMapping("/admin/list")
    public String adminList(@RequestBody PageVO pageVO){
        JSONObject json = new JSONObject();
        json.put("code",20000);
        Map<String, Object> map = new HashMap<String, Object>();
        if(pageVO.getId()!=0&&!pageVO.getUsername().equals("")){
            Page<Admin> page = new Page<>(pageVO.getPage(),pageVO.getLimit());
            adminMapper.selectPage(page,new QueryWrapper<Admin>().like("username",pageVO.getUsername()).eq("id",pageVO.getId()));
            List<Admin> admins = page.getRecords();
            map.put("list",admins);
            map.put("total",page.getTotal());
        }else {
            if(pageVO.getId()!=0) {
                map= adminService.adminListById(pageVO.getId(),pageVO.getPage(), pageVO.getLimit());
            }
            if(!pageVO.getUsername().equals("")){
                map= adminService.adminListByName(pageVO.getUsername(),pageVO.getPage(), pageVO.getLimit());
            }
        }
        if (pageVO.getId()==0&&pageVO.getUsername().equals("")){
            map = adminService.adminList(pageVO.getPage(), pageVO.getLimit());
        }
        json.put("data",map);
        return  json.toJSONString();

    }

    @PostMapping("/admin/create")
    public String creatAdmin(@RequestBody Map<String, String> map){

        JSONObject json = new JSONObject();
        json.put("code",20000);
        Result result = new Result();
        result.setResult(3);
        if(!Objects.equals(map.get("password"), map.get("comfirm_password"))){
            result.setResult(2);
            json.put("data",result);
            return json.toJSONString();
        }else {
            Admin admin = new Admin();
            admin.setUsername(map.get("username"));
            admin.setPassword(map.get("password"));
            result.setResult(adminService.createAdmin(admin));
            json.put("data",result);
            return json.toJSONString();
        }
    }

    @PostMapping("/admin/update")
    public String updateAdmin(@RequestBody Map<String, String> map){
        JSONObject json = new JSONObject();
        json.put("code",20000);
        Result result = new Result();
        result.setResult(2);
        if(!Objects.equals(map.get("password"), map.get("comfirm_password"))) {
            result.setResult(1);
            json.put("data", result);
            return json.toJSONString();
        }else {
            Admin admin = new Admin();
            admin.setId(Integer.parseInt(map.get("id")));
            admin.setPassword(map.get("password"));
            result.setResult(adminService.updatePassword(admin));
            json.put("data",result);
            return json.toJSONString();
        }
    }

    @PostMapping("/admin/delete")
    public String deleteAdmin(@RequestBody Map<String, String> map){
        JSONObject json = new JSONObject();
        json.put("code",20000);
        Result result = new Result();
        result.setResult(1);
        Admin admin = new Admin();
        admin.setId(Integer.parseInt(map.get("id")));
        result.setResult(adminService.deleteAdmin(admin));
        json.put("data",result);
        return json.toJSONString();
        }
}
