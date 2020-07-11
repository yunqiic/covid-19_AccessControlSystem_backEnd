package com.springboot.final_project.controller;

import com.alibaba.fastjson.JSONObject;
import com.springboot.final_project.Entity.Admin;
import com.springboot.final_project.Entity.Inspectors;
import com.springboot.final_project.Entity.Result;
import com.springboot.final_project.VO.PageVO;
import com.springboot.final_project.service.inspectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping("/account/inspector")
public class inspectorController {

    @Autowired
    private inspectorService inspectorService;

    @GetMapping("/list")
    public String adminList(@RequestBody PageVO pageVO){
        JSONObject json = new JSONObject();
        json.put("code",20000);
        Map<String, Object> map = new HashMap<String, Object>();
        if(pageVO.getId()!=0) {
            map= inspectorService.inspectorListById(pageVO.getId(),pageVO.getPage(), pageVO.getLimit());
        }
        if(!pageVO.getUsername().equals("")){
            map= inspectorService.inspectorListByName(pageVO.getUsername(),pageVO.getPage(), pageVO.getLimit());
        }
        if (pageVO.getId()==0&&pageVO.getUsername().equals("")){
            map = inspectorService.inspectorList(pageVO.getPage(), pageVO.getLimit());
        }
        json.put("data",map);
        return  json.toJSONString();

    }

    @PostMapping("/create")
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
            Inspectors inspectors = new Inspectors();
            inspectors.setUsername(map.get("username"));
            inspectors.setPassword(map.get("password"));
            result.setResult(inspectorService.createInspector(inspectors));
            json.put("data",result);
            return json.toJSONString();
        }
    }

    @PostMapping("/update")
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
            Inspectors inspectors = new Inspectors();
            inspectors.setId(Integer.parseInt(map.get("id")));
            inspectors.setPassword(map.get("password"));
            result.setResult(inspectorService.updatePassword(inspectors));
            json.put("data",result);
            return json.toJSONString();
        }
    }

    @PostMapping("/delete")
    public String deleteAdmin(@RequestBody Map<String, String> map){
        JSONObject json = new JSONObject();
        json.put("code",20000);
        Result result = new Result();
        result.setResult(1);
        Inspectors inspectors = new Inspectors();
        inspectors.setId(Integer.parseInt(map.get("id")));
        result.setResult(inspectorService.deleteInspector(inspectors));
        json.put("data",result);
        return json.toJSONString();
    }

    @PostMapping("/reset-openid")
    public String resetOpenid(@RequestBody Map<String, String> map){
        JSONObject json = new JSONObject();
        json.put("code",20000);
        Result result = new Result();
        result.setResult(1);
        Inspectors inspectors = new Inspectors();
        inspectors.setId(Integer.parseInt(map.get("id")));
        inspectors.setOpenid("");
        result.setResult(inspectorService.resetOpenid(inspectors));
        json.put("data",result);
        return json.toJSONString();
    }


}
