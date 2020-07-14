package com.springboot.final_project.service;

import com.springboot.final_project.Entity.User;

import java.util.Map;

public interface RecordFormService {

    String getListByUserId(int user_id);

    //获取10条
    String getList(String id);

    //按筛选条件获取出入记录列表
    String getListByAll(String id, String resident_id, String inspector_id, String date_begin,String date_end,
                        String health_query, int page, int limit, String sort);

    String delete(String id);

}
