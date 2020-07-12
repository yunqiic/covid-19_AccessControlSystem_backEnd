package com.springboot.final_project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.springboot.final_project.Entity.RecordForm;
import com.springboot.final_project.Entity.User;
import com.springboot.final_project.mapper.RecordFormMapper;
import com.springboot.final_project.mapper.UserMapper;
import com.springboot.final_project.service.analysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class analysisServiceImpl implements analysisService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RecordFormMapper recordFormMapper;

    @Override
    public List<Integer> analysisHealth() {
        int health_count = userMapper.selectCount(new QueryWrapper<User>().lt("health_status",3));
        int unhealth_count = userMapper.selectCount(new QueryWrapper<User>().ge("health_status",3));
        List<Integer> list = new ArrayList<Integer>();
        list.add(health_count);
        list.add(unhealth_count);
        return list;
    }

    @Override
    public Map<String, Object> analysisRecord(int option) {
        List<RecordForm> recordForms;
        Map <String, Object> map = new HashMap<>();
        Date date = new Date();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
//        System.out.println("今天的日期："+df.format(new Date(date.getTime() - 2 * 24 * 60 * 60 * 1000)));
//        System.out.println("今天的日期："+df.format(date));
//        String date1 = new String(df.format(date));
        try {
            List<String> access_dates = new ArrayList<>();
            List<Integer> access_times = new ArrayList<>();
            RecordForm recordForm = new RecordForm();
            Date date1,date3,date4;
            if(option>20){
                date1 = df.parse(df.format(new Date(date.getTime() - (option-1-20) * 24 * 60 * 60 * 1000 - 20 * 24 * 60 * 60 * 1000)));
            }
            else {
                date1 = df.parse(df.format(new Date(date.getTime() - (option-1) * 24 * 60 * 60 * 1000)));
            }
            Date date2 = df.parse(df.format(new Date(date.getTime() + 24 * 60 * 60 * 1000)));
            recordForms = recordFormMapper.selectList(new QueryWrapper<RecordForm>().between("time",date1,date2));
            for (int i=0 ;i<option;i++){
                if(option>20){
                    access_dates.add(df.format(new Date(date.getTime() - (option-i-1-20) * 24 * 60 * 60 * 1000 -20* 24 * 60 * 60 * 1000)));
                }else {
                    access_dates.add(df.format(new Date(date.getTime() - (option-i-1) * 24 * 60 * 60 * 1000)));
                }
                date3 = df.parse(access_dates.get(i));
                date4 = df.parse(df.format(new Date(date3.getTime() + 24 * 60 * 60 * 1000)));
                int count = recordFormMapper.selectCount(new QueryWrapper<RecordForm>().between("time",date3,date4));
                access_times.add(count);

            }
            map.put("access_dates",access_dates);
            map.put("access_times",access_times);
            System.out.println(date1+"===="+date2);
            System.out.println(recordForms.toString());
            System.out.println(access_dates.toString());
            System.out.println(access_times.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return map;
    }
}
