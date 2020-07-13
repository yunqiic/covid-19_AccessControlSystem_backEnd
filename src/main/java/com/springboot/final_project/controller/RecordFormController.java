package com.springboot.final_project.controller;

import com.springboot.final_project.Entity.RecordForm;
import com.springboot.final_project.service.RecordFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/record")
public class RecordFormController {

    @Autowired
    RecordFormService recordFormService;

    //1.按筛选条件获取出入记录列表
    @RequestMapping("/list")
    public String getList(String id,String resident_id,String inspector_id,String date_query[],
                          String health_query,int page,int limit,String sort){
        System.out.println(id);
        System.out.println(resident_id);
        System.out.println(inspector_id);
        System.out.println(date_query);
        System.out.println(health_query);
        System.out.println(page);
        System.out.println(limit);
        System.out.println(sort);
        return "123";

        }


}
