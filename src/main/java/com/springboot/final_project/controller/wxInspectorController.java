package com.springboot.final_project.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.springboot.final_project.Entity.Inspectors;
import com.springboot.final_project.Entity.RecordForm;
import com.springboot.final_project.Entity.User;
import com.springboot.final_project.mapper.InspectorsMapper;
import com.springboot.final_project.mapper.RecordFormMapper;
import com.springboot.final_project.service.wxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.final_project.service.inspectorService;

import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping("/wx/inspector")
public class wxInspectorController {

    @Autowired
    inspectorService inspectorService;

    @Autowired
    InspectorsMapper inspectorsMapper;

    @Autowired
    RecordFormMapper recordFormMapper;

    @Autowired
    wxService wxService;


    //1.获取openid 并 检测是否绑定了openid
    @RequestMapping("/getOpenid")
    public String getOpenid(String code){
        return inspectorService.getOpenid(code);
    }

    //2.账号密码登陆
    @RequestMapping("/login")
    public String InspectorLogin(String username,String password){
        JSONObject result = new JSONObject();
        Inspectors inspectors = inspectorsMapper.selectOne(new QueryWrapper<Inspectors>().eq("username",username));
        try {
            if (inspectors == null) {
                result.put("id", 0);
                result.put("result", 2);//信号量  用户名不存在:2
                return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
            }
            if (inspectors.getPassword().equals(password)) {
                result.put("id", inspectors.getId());
                result.put("result", 0);//信号量  成功:0
                return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
            } else {
                result.put("id", 0);
                result.put("result", 1);//信号量 密码错误:1
                return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
            }
        }catch (Exception e) {
            e.printStackTrace();
            result.put("id", 0);
            result.put("result", 3);//信号量 读写错误:3
            return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
        }
    }

    //3.微信登陆
    @RequestMapping("/wx-login")
    public String WxInspectorLogin(String openid){
        JSONObject result = new JSONObject();
        try{
            Inspectors inspectors = inspectorsMapper.selectOne(new QueryWrapper<Inspectors>().eq("openid",openid));
            result.put("id",inspectors.getId());
            result.put("result",0);//信号量  成功:0   读写错误:1
            return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
        }catch (Exception e) {
            e.printStackTrace();
            result.put("id", 0);
            result.put("result", 1);//信号量 读写错误:1
            return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
        }
    }

    //4.检查员修改密码
    @RequestMapping("/change-pwd")
    public String changePwd(int id,String old_password,String password,String comfirm_password) {
        return inspectorService.ChangePwd(id,old_password,password,comfirm_password);
    }

    //5.检查员解除绑定
    @RequestMapping("/cancel-bind")
    public String cancelBind(int id) {
        return inspectorService.CancelBind(id);
    }

    //6.检查员绑定新微信
    @RequestMapping("/bind")
    public String residentBind(int id,String openid){
        return inspectorService.Bind(id,openid);
    }

    //7.是否已绑定微信（判断住户的openid是否为空）
    @RequestMapping("/if-bind")
    public String IfBind(int id){
        return inspectorService.IfBind(id);
    }

    //8.录入体温
    @RequestMapping("/create-record")
    public String createRecord(String time,double temperature,int resident_id,int inspector_id){
        JSONObject result = new JSONObject();
        try{
            Long longTime= Long.valueOf(time);
            Date date = new Date(longTime);
            System.out.println(date);
            RecordForm recordForm = new RecordForm();
            recordForm.setUser_id(resident_id);
            recordForm.setTemperature(temperature);
            recordForm.setInspectors_id(inspector_id);
            recordForm.setTime(date);
            recordFormMapper.insert(recordForm);
            result.put("result",0);//信号量  成功:0   读写错误:1
            return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
        }catch (Exception e) {
            e.printStackTrace();
            result.put("id", 0);
            result.put("result", 1);//信号量 读写错误:1
            return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
        }
    }
}
