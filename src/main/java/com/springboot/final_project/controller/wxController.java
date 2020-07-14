package com.springboot.final_project.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.zxing.WriterException;
import com.springboot.final_project.Entity.Admin;
import com.springboot.final_project.Entity.User;
import com.springboot.final_project.mapper.AdminMapper;
import com.springboot.final_project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@CrossOrigin
@Controller
public class wxController {

    @Autowired
    wxService wxService;

    @Autowired
    waterService waterService;

    @Autowired
    UserService userService;

    @Autowired
    RecordFormService recordFormService;

    @Autowired
    AdminMapper adminMapper;

    //去到后台登陆页面
    @RequestMapping("/")
    public String index(){
        return "index.html";
    }

    @RequestMapping("/admin/login")
    @ResponseBody
    public String adminLogin(Admin admin){
        JSONObject jsonObject = new JSONObject();
        QueryWrapper<Admin> wrapper = new QueryWrapper();

        Admin admin1 = adminMapper.selectOne(wrapper.eq("username", admin.getUsername()));
        if(admin1==null || !admin.getPassword().equals(admin1.getPassword())){
            jsonObject.put("message","用户名 或 密码错误");
        }
        else{
            jsonObject.put("code",20000);
            jsonObject.put("data","admin-token");
        }

        return jsonObject.toJSONString();
//        return wxService.adminLogin();
    }

    //登出
    @RequestMapping("/admin/logout")
    @ResponseBody
    public String adminLogout(){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",20000);
        jsonObject.put("data",0);
        return jsonObject.toJSONString();

    }

    @RequestMapping("/admin/info")
    @ResponseBody
    public String adminInfo(){
        JSONObject data = new JSONObject();

        data.put("roles","admin");
        data.put("introduction","I am a super administrator");
        data.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        data.put("name","Super Admin");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",20000);
        jsonObject.put("data",data);
        return jsonObject.toJSONString();
//        return wxService.adminInfo();
    }

    //防水墙
    @CrossOrigin
    @RequestMapping("/waterCheck")
    @ResponseBody
    public String waterCheck(String ticket, String randstr) {
        return waterService.waterCheck(ticket,randstr);
    }


    //微信住户后端：

    //0.获取openid
    @RequestMapping("/wx/resident/getOpenid")
    @ResponseBody
    public String getOpenid(String code){
        return wxService.getOpenid(code);
    }

    //1.注册账号
    @RequestMapping("/wx/resident/register")
    @ResponseBody
    public String ResidentRegister(User user,String confirm_password){
        JSONObject result = new JSONObject();
        user.setHealth_status(0);
        user.setIs_locked(false);
        user.setAccess_times(0);
        if("".equals(confirm_password) ||"".equals(user.getPassword())||(!user.getPassword().equals(confirm_password))){
            result.put("id",0);
            result.put("result",2);//信号量  成功:0   用户名已存在:1   两次密码不一致:2  读写错误:3
            return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
        }
        User user2 = userService.getByUserName(user.getUsername());
        //如果找到了
        if(user2!=null){
            result.put("id",0);
            result.put("result",1);
            return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
        }
        try {
            if (userService.add(user) ==1 ){
                User user3 = userService.getByUserName(user.getUsername());
                result.put("id", user3.getId());
                result.put("result",0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("失败，事务回滚");
            result.put("id", 0);
            result.put("result", 3);
        }
        return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
    }

    //2.微信住户账号密码登陆
    @RequestMapping("/wx/resident/login")
    @ResponseBody
    public String ResidentLogin(String username,String password){
        JSONObject result = new JSONObject();
        User user = userService.getByUserName(username);
        try {
            if (user == null) {
                result.put("id", 0);
                result.put("result", 2);//信号量  用户名不存在:2
                return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
            }
            if (user.getPassword().equals(password)) {
                result.put("id", user.getId());
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

    //3.微信住户登陆
    @RequestMapping("/wx/resident/wx-login")
    @ResponseBody
    public String WxResidentLogin(String openid){
        JSONObject result = new JSONObject();
        try{
            User user = userService.getByOpenid(openid);
            result.put("id",user.getId());
            result.put("result",0);//信号量  成功:0   读写错误:1
            return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
        }catch (Exception e) {
            e.printStackTrace();
            result.put("id", 0);
            result.put("result", 1);//信号量 读写错误:1
            return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
        }
    }

    //4.展示二维码
    @RequestMapping("/wx/resident/qrcode")
    @ResponseBody
    public String getQRcode(int id) throws WriterException, IOException {
        return wxService.getQRcode(id);
    }

    //5.出入记录
    @RequestMapping("/wx/resident/record")
    @ResponseBody
    public String getRecord(int id){
        return recordFormService.getListByUserId(id);
    }

    //6.住户个人信息展示
    @RequestMapping("/wx/resident/info")
    @ResponseBody
    public String getInfo(int id) {
        return wxService.getInfo(id);
    }

    //11。住户修改个人信息
    @RequestMapping("/wx/resident/edit")
    @ResponseBody
    public String ResidentEdit(int id,int sex,String identity_card,String house_no,String photo){
        return userService.Edit(id,sex,identity_card,house_no,photo);
    }

    //7.住户修改密码
    @RequestMapping("/wx/resident/change-pwd")
    @ResponseBody
    public String changePwd(int id,String old_password,String password,String confirm_password) {
        return userService.ChangePwd(id,old_password,password,confirm_password);
    }

    //8.住户解除绑定
    @RequestMapping("/wx/resident/cancel-bind")
    @ResponseBody
    public String cancelBind(int id) {
        return userService.CancelBind(id);
    }

    //9.账号登陆的住户绑定新微信
    @RequestMapping("/wx/resident/bind")
    @ResponseBody
    public String residentBind(int id,String openid){
        return userService.Bind(id,openid);
    }

    //10.是否已绑定微信（判断住户的openid是否为空）
    @RequestMapping("/wx/resident/if-bind")
    @ResponseBody
    public String IfBind(int id){
        return userService.IfBind(id);
    }





}
