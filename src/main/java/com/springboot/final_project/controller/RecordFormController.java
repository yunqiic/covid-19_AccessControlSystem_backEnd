package com.springboot.final_project.controller;

import com.springboot.final_project.Entity.RecordForm;
import com.springboot.final_project.service.RecordFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/record")
public class RecordFormController {

    @Autowired
    RecordFormService recordFormService;

    //1.按筛选条件获取出入记录列表
    @RequestMapping("/list")
    public String getList(String id, String resident_id, String inspector_id, String date_begin,String date_end,
                          String health_query, int page, int limit, String sort){
        return recordFormService.getListByAll(id,resident_id,inspector_id,date_begin,date_end,health_query,page,limit,sort);

    }

    //2.根据住户id查询某住户最近出入记录
    @RequestMapping("/recent")
    @ResponseBody
    public String getRecent(String id){
        return recordFormService.getList(id);
    }

    //3.根据记录id删除记录
    @RequestMapping("/delete")
    @ResponseBody
    public String delete(String id){
        return recordFormService.delete(id);
    }
}
