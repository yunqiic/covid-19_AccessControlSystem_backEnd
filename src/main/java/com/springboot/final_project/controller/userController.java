package com.springboot.final_project.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.springboot.final_project.Entity.Result;
import com.springboot.final_project.Entity.User;
import com.springboot.final_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/account/resident")
public class userController
{

    @Autowired
    UserService userService;

    //增加住户
    @RequestMapping("/create")
    public String list(User user){
        JSONObject result = new JSONObject();
        if(user.getHealth_status()==null)user.setHealth_status(0);
        if(user.getIs_locked()==null)user.setIs_locked(false);
        if(user.getAccess_times()==null)user.setAccess_times(0);
        try {
            if (userService.add(user) ==1 ){
                result.put("code", 1);
                result.put("msg", "添加成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("失败，事务回滚");
            result.put("code", 0);
            result.put("msg", "添加失败");
        }
        return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
    }

    //以id删除用户
    //信号量  成功:0   失败:1
    @PostMapping("/delete")
    public String deleteUser(@RequestParam String id) {
        JSONObject result = new JSONObject();
        result.put("code", 20000);
        Result result2 = new Result();
        result2.setResult(1);
        try {
            userService.delete(Integer.valueOf(id).intValue());
            result2.setResult(0);
            result.put("data",result2);
        } catch (Exception e) {
            result.put("data",result2);
        }
        return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
    }

    ////按筛选条件获取出入记录列表
    @RequestMapping("/list")
    public String getUserInfo(User user, String locked_query, String health_query, int page, int limit, String sort) {
        JSONObject result = new JSONObject();
        result.put("code", 20000);
        if(locked_query.equals("locked"))user.setIs_locked(true);
        else if(locked_query.equals("unlocked")) user.setIs_locked(false);
        else;

        if(health_query.equals("healthy"))user.setHealth_status(0);
        else if(health_query.equals("unhealthy"))user.setHealth_status(1);
        else;
        System.out.println(user);
        Map<String, Object> map = new HashMap<String, Object>();
        map = userService.getList(user,page, limit,sort);
        result.put("data",map);

        return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
    }

    //以id锁定用户
    //信号量  成功:0   失败:1
    @PostMapping("/lock")
    public String lockUser(@RequestParam String id){
        JSONObject json = new JSONObject();
        json.put("code",20000);
        Result result = new Result();
        result.setResult(1);
        try {
            userService.lock(Integer.valueOf(id).intValue());
            result.setResult(0);
            json.put("data",result);
        } catch (Exception e) {
            json.put("data",result);
        }
        return JSON.toJSONString(json, SerializerFeature.DisableCircularReferenceDetect);
    }


    //以id解锁用户
    //信号量  成功:0   失败:1
    @PostMapping("/unlock")
    public String unLockUser(@RequestParam String id){
        JSONObject json = new JSONObject();
        json.put("code",20000);
        Result result = new Result();
        result.setResult(1);
        try {
            userService.unLock(Integer.valueOf(id).intValue());
            result.setResult(0);
            json.put("data",result);
        } catch (Exception e) {
            json.put("data",result);
        }
        return JSON.toJSONString(json, SerializerFeature.DisableCircularReferenceDetect);
    }

    //根据住户id查询其个人信息
    @RequestMapping("/info")
    public String getUserById(int id){
        JSONObject result = new JSONObject();
        result.put("code", 20000);
        User user = userService.getById(id);
        result.put("info",user);
        return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
    }


}
