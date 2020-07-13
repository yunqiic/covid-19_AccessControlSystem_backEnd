package com.springboot.final_project.service;

import com.springboot.final_project.Entity.Admin;
import com.springboot.final_project.Entity.Inspectors;

import java.util.Map;

public interface inspectorService {

    Map<String,Object> inspectorListById(int id, int index, int limit);

    Map<String,Object> inspectorListByName(String name,int index, int limit);

    Map<String,Object> inspectorList(int index , int limit);

    int createInspector(Inspectors inspectors);

    int updatePassword(Inspectors inspectors);

    int deleteInspector(Inspectors inspectors);

    int resetOpenid(Inspectors inspectors);

    String getOpenid(String code);

    String ChangePwd(int id,String old_password,String password,String comfirm_password);

    String CancelBind(int id);

    String Bind(int id,String openid);

    String IfBind(int id);
}
