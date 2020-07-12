package com.springboot.final_project.controller;

import com.alibaba.fastjson.JSONObject;
import com.springboot.final_project.VO.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.springboot.final_project.service.analysisService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/analysis")
public class analysisController {

    @Autowired
    analysisService analysisService;

    @GetMapping("/health")
    public String analysisHealth(){
        JSONObject json = new JSONObject();
        json.put("code",20000);
        Map<String, Object> map = new HashMap<String, Object>();
        List<Integer> list = analysisService.analysisHealth();
        map.put("health_count",list);
        json.put("data",map);
        return  json.toJSONString();
    }


}
