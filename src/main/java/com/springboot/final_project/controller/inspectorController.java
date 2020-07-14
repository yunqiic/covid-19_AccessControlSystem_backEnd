package com.springboot.final_project.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.final_project.Entity.Admin;
import com.springboot.final_project.Entity.Inspectors;
import com.springboot.final_project.Entity.Result;
import com.springboot.final_project.VO.PageVO;
import com.springboot.final_project.mapper.InspectorsMapper;
import com.springboot.final_project.service.inspectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/account/inspector")
public class inspectorController {

    @Autowired
    private inspectorService inspectorService;

    @Autowired
    private InspectorsMapper inspectorsMapper;

    //1.按筛选条件获取检查员列表
    @GetMapping("/list")
    public String adminList(Inspectors inspectors,int page,int limit,String sort){
        JSONObject json = new JSONObject();
        json.put("code",20000);
        Map<String, Object> map = new HashMap<String, Object>();
        Page<Inspectors> pages = new Page<>(page,limit);
        if(inspectors.getId()!=null&&inspectors.getUsername()!=null){
            inspectorsMapper.selectPage(pages,new QueryWrapper<Inspectors>().like("username",inspectors.getUsername()).like("id",inspectors.getId()));
        }else {
            if(inspectors.getId()!=null) {
                inspectorsMapper.selectPage(pages,new QueryWrapper<Inspectors>().like("id",inspectors.getId()));
            }
            if(inspectors.getUsername()!=null){
                inspectorsMapper.selectPage(pages,new QueryWrapper<Inspectors>().like("username",inspectors.getUsername()));
            }
        }

        if (inspectors.getId()==null&&inspectors.getUsername()==null){
            inspectorsMapper.selectPage(pages,new QueryWrapper<Inspectors>());
        }
        List<Inspectors> inspector = pages.getRecords();
        if(sort.equals("-id")) Collections.reverse(inspector);
        map.put("list",inspector);
        map.put("total",pages.getTotal());
        json.put("data",map);
        return  json.toJSONString();

    }

    //2.添加检查员
    @PostMapping("/create")
    public String creatInspector(Inspectors inspectors,String confirm_password,String new_password){
        JSONObject json = new JSONObject();
        json.put("code",20000);
        Map<String, Object> map = new HashMap<String, Object>();
        //信号量  成功:0   用户名已存在:1   两次密码不一致:2   读写错误:3
        if(!Objects.equals(new_password, confirm_password)){
            map.put("id",0);
            map.put("result",2);
            json.put("data",map);
            return json.toJSONString();
        }else {
            inspectors.setPassword(new_password);
            map = inspectorService.createInspector(inspectors);
            json.put("data",map);
            return json.toJSONString();
        }
    }

    //3.修改检查员密码
    //信号量  成功:0   两次密码不一致:1   旧密码不正确:2   读写错误:3
    @PostMapping("/update")
    public String updateAdmin(String id,String old_password,String new_password,String confirm_password){
        JSONObject json = new JSONObject();
        json.put("code",20000);
        Result result = new Result();
        try {
            if (!Objects.equals(new_password, confirm_password)) {
                result.setResult(1);
                json.put("data", result);
                return json.toJSONString();
            } else {
                Inspectors inspectors = inspectorsMapper.selectById(id);
                if (!Objects.equals(inspectors.getPassword(), old_password)) {
                    result.setResult(2);
                    json.put("data", result);
                    return json.toJSONString();
                }
                inspectors.setPassword(new_password);
                result.setResult(inspectorService.updatePassword(inspectors));
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

    //4.删除检查员
    //信号量  成功:0   失败:1
    @PostMapping("/delete")
    public String deleteAdmin(int id){
        JSONObject json = new JSONObject();
        json.put("code",20000);
        Result result = new Result();
        try {
            Inspectors inspectors = new Inspectors();
            inspectors.setId(id);
            result.setResult(inspectorService.deleteInspector(inspectors));
        }catch (Exception e) {
            e.printStackTrace();
            result.setResult(1);
        }
        json.put("data",result);
        return json.toJSONString();
    }

    //5.初始化检查员openid
    //信号量  成功:0   失败:1
    @PostMapping("/reset-openid")
    public String resetOpenid(int id){
        JSONObject json = new JSONObject();
        json.put("code",20000);
        Result result = new Result();
        result.setResult(1);
        try {
            Inspectors inspectors = new Inspectors();
            inspectors.setId(id);
            inspectors.setOpenid("");
            result.setResult(inspectorService.resetOpenid(inspectors));
        }catch (Exception e) {
            e.printStackTrace();
            result.setResult(1);
        }
        json.put("data",result);
        return json.toJSONString();
    }


}
