package com.springboot.final_project.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.final_project.Entity.Admin;
import com.springboot.final_project.Entity.RecordForm;
import com.springboot.final_project.mapper.RecordFormMapper;
import com.springboot.final_project.service.RecordFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RecordFormServiceImpl implements RecordFormService {
    @Autowired
    RecordFormMapper recordFormMapper;

    @Override
    public String getListByUserId(int user_id){
        List<RecordForm> list = recordFormMapper.selectList(new QueryWrapper<RecordForm>().eq("user_id",user_id));
        JSONObject result = new JSONObject();
        result.put("list",list);
        HashMap<String, Object> map = new HashMap<String, Object>();
        System.out.println(result);
        return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);

    }

    @Override
    public String getList(String id){
        JSONObject result = new JSONObject();
        result.put("code",20000);
        Page<RecordForm> page = new Page<>(1,10);
        recordFormMapper.selectPage(page, new QueryWrapper<RecordForm>().eq("user_id",Integer.parseInt(id)));
        List<RecordForm> recordForms = page.getRecords();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("rcData",recordForms);
        result.put("data",map);
        return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);

    }

    //按条件筛选
    @Override
    public String getListByAll(String id, String resident_id, String inspector_id, String date_begin,
                               String date_end, String health_query, int page, int limit, String sort)
    {
        JSONObject result = new JSONObject();
        result.put("code",20000);
        Page<RecordForm> pages = new Page<>(page,limit);
        if(id==null)id="";
        if(resident_id==null)resident_id="";
        if(inspector_id==null)inspector_id="";
        if("".equals(date_begin) || date_begin==null)date_begin="1";
        System.out.println("11111111");
        System.out.println(date_begin);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String a = sdf.format(new Date(Long.parseLong(date_begin)));
        if("".equals(date_end) || date_end==null)date_end="9999999999999";

        String b = sdf.format(new Date(Long.parseLong(date_end)));
        if("".equals(health_query) || health_query==null){
            recordFormMapper.selectPage(pages,new QueryWrapper<RecordForm>().like("id",id).like("user_id",resident_id)
                    .like("inspectors_id",inspector_id).ge("time",a).lt("time",b));
        }
        else if(health_query.equals("healthy")){
            recordFormMapper.selectPage(pages,new QueryWrapper<RecordForm>().like("id",id).like("user_id",resident_id)
                    .like("inspectors_id",inspector_id).ge("time",a).lt("time",b).lt("temperature",37.3));
        }
        else{
            recordFormMapper.selectPage(pages,new QueryWrapper<RecordForm>().like("id",id).like("user_id",resident_id)
                    .like("inspectors_id",inspector_id).ge("time",a).lt("time",b).ge("temperature",37.3));
        }
        //只查id

        List<RecordForm> recordForm = pages.getRecords();
        if(sort.equals("-id")) Collections.reverse(recordForm);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("list",recordForm);
        map.put("total",pages.getTotal());

        result.put("data",map);
        System.out.println(recordForm);
        return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public String delete(String id){
        JSONObject result = new JSONObject();
        result.put("code",20000);
        HashMap<String, Object> map = new HashMap<String, Object>();
        QueryWrapper<RecordForm> wrapper = new QueryWrapper();
        try {
            recordFormMapper.deleteById(id);
            map.put("result",0);
        }catch (Exception e) {
            e.printStackTrace();
            map.put("result",1);
        }
        result.put("data",map);
        return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);

    }


}
