package com.springboot.final_project.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.springboot.final_project.Entity.RecordForm;
import com.springboot.final_project.mapper.RecordFormMapper;
import com.springboot.final_project.service.RecordFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RecordFormServiceImpl implements RecordFormService {
    @Autowired
    RecordFormMapper recordFormMapper;

    @Override
    public String getListByUserId(int user_id){
        List<RecordForm> list = recordFormMapper.selectList(new QueryWrapper<RecordForm>().eq("user_id",user_id));
        RecordForm recordForm = list.get(0);
        System.out.println(recordForm);
        JSONObject result = new JSONObject();
        result.put("list",list);
        return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);

    }
}
