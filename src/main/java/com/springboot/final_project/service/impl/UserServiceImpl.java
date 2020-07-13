package com.springboot.final_project.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.final_project.Entity.User;
import com.springboot.final_project.mapper.UserMapper;
import com.springboot.final_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public int add(User user){
        return userMapper.insert(user);
    }

    @Override//按筛选条件获取住户列表
    public Map<String,Object> getList(User user, int index, int limit, String sort) {
        Page<User> page = new Page<>(index,limit);
        //1只查id
        if(user.getId()!= null && user.getName().equals("") && user.getIs_locked()==null && user.getHealth_status()==null) {
            userMapper.selectPage(page, new QueryWrapper<User>().like("id", user.getId()));
        }
        //2只查name
        else if(user.getId() == null && !user.getName().equals("") &&user.getIs_locked()==null&&user.getHealth_status()==null)
            userMapper.selectPage(page,new QueryWrapper<User>().like("name",user.getName()));
        //3只查锁定
        else if(user.getId() == null && user.getName().equals("") && user.getIs_locked()!=null && user.getHealth_status()==null){
            if(user.getIs_locked()==true)userMapper.selectPage(page,new QueryWrapper<User>().eq("is_locked",1));
            else userMapper.selectPage(page,new QueryWrapper<User>().eq("is_locked",0));
        }
        //4只查健康
        else if(user.getId() == null && user.getName().equals("") && user.getIs_locked()==null && user.getHealth_status()!=null){
            //healthy
            if(user.getHealth_status()==0)userMapper.selectPage(page,new QueryWrapper<User>().lt("health_status",3));//小于3
            else userMapper.selectPage(page,new QueryWrapper<User>().ge("health_status",3));//大于等于3
        }
        //5查id和name
        else if(user.getId()!= null && !user.getName().equals("") && user.getIs_locked()==null && user.getHealth_status()==null){
            userMapper.selectPage(page, new QueryWrapper<User>().like("id", user.getId()).like("name",user.getName()));
        }
        //6查id和锁定
        else if(user.getId()!= null && user.getName().equals("") && user.getIs_locked()!=null && user.getHealth_status()==null){
            if(user.getIs_locked()==true)userMapper.selectPage(page,new QueryWrapper<User>().eq("is_locked",1).like("id", user.getId()));
            else userMapper.selectPage(page,new QueryWrapper<User>().eq("is_locked",0).like("id", user.getId()));
        }
        //7查id和健康
        else if(user.getId() != null && user.getName().equals("") && user.getIs_locked()==null && user.getHealth_status()!=null){
            //healthy
            if(user.getHealth_status()==0)userMapper.selectPage(page,new QueryWrapper<User>().lt("health_status",3).like("id", user.getId()));//小于3
            else userMapper.selectPage(page,new QueryWrapper<User>().ge("health_status",3).like("id", user.getId()));//大于等于3
        }
        //8查name和锁定
        else if(user.getId()== null && !user.getName().equals("") && user.getIs_locked()!=null && user.getHealth_status()==null){
            if(user.getIs_locked()==true)userMapper.selectPage(page,new QueryWrapper<User>().eq("is_locked",1).like("name",user.getName()));
            else userMapper.selectPage(page,new QueryWrapper<User>().eq("is_locked",0).like("name",user.getName()));
        }
        //9name和健康
        else if(user.getId() == null && !user.getName().equals("") && user.getIs_locked()==null && user.getHealth_status()!=null){
            //healthy
            if(user.getHealth_status()==0)userMapper.selectPage(page,new QueryWrapper<User>().lt("health_status",3).like("name",user.getName()));//小于3
            else userMapper.selectPage(page,new QueryWrapper<User>().ge("health_status",3).like("name",user.getName()));//大于等于3
        }
        //10锁定和健康
        else if(user.getId() == null && user.getName().equals("") && user.getIs_locked()!=null && user.getHealth_status()!=null){
            if(user.getIs_locked()==true){
                if(user.getHealth_status()==0){
                    userMapper.selectPage(page,new QueryWrapper<User>().lt("health_status",3).eq("is_locked",1));//小于3
                }
                else {
                    userMapper.selectPage(page,new QueryWrapper<User>().ge("health_status",3).eq("is_locked",1));//大于等于3
                }
            }
            else {
                if(user.getHealth_status()==0){
                    userMapper.selectPage(page,new QueryWrapper<User>().lt("health_status",3).eq("is_locked",0));//小于3
                }
                else {
                    userMapper.selectPage(page,new QueryWrapper<User>().ge("health_status",3).eq("is_locked",0));//大于等于3
                }
            }
        }
        //11查id、name、锁定
        else if(user.getId()!= null && !user.getName().equals("") && user.getIs_locked()!=null && user.getHealth_status()==null){
            if(user.getIs_locked()==true)userMapper.selectPage(page,new QueryWrapper<User>().eq("is_locked",1).like("id", user.getId()).like("name",user.getName()));
            else userMapper.selectPage(page,new QueryWrapper<User>().eq("is_locked",0).like("id", user.getId()).like("name",user.getName()));
        }
        //12查id、name、健康
        else if(user.getId() != null && !user.getName().equals("") && user.getIs_locked()==null && user.getHealth_status()!=null){
            //healthy
            if(user.getHealth_status()==0)userMapper.selectPage(page,new QueryWrapper<User>().lt("health_status",3).like("id", user.getId()).like("name",user.getName()));//小于3
            else userMapper.selectPage(page,new QueryWrapper<User>().ge("health_status",3).like("id", user.getId()).like("name",user.getName()));//大于等于3
        }
        //13查name、锁定、健康
        else if(user.getId() == null && !user.getName().equals("") && user.getIs_locked()!=null && user.getHealth_status()!=null){
            if(user.getIs_locked()==true){
                if(user.getHealth_status()==0){
                    userMapper.selectPage(page,new QueryWrapper<User>().lt("health_status",3).eq("is_locked",1).like("name",user.getName()));//小于3
                }
                else {
                    userMapper.selectPage(page,new QueryWrapper<User>().ge("health_status",3).eq("is_locked",1).like("name",user.getName()));//大于等于3
                }
            }
            else {
                if(user.getHealth_status()==0){
                    userMapper.selectPage(page,new QueryWrapper<User>().lt("health_status",3).eq("is_locked",0).like("name",user.getName()));//小于3
                }
                else {
                    userMapper.selectPage(page,new QueryWrapper<User>().ge("health_status",3).eq("is_locked",0).like("name",user.getName()));//大于等于3
                }
            }
        }
        //14查id、锁定、健康
        else if(user.getId() != null && user.getName().equals("") && user.getIs_locked()!=null && user.getHealth_status()!=null){
            if(user.getIs_locked()==true){
                if(user.getHealth_status()==0){
                    userMapper.selectPage(page,new QueryWrapper<User>().lt("health_status",3).eq("is_locked",1).like("id", user.getId()));//小于3
                }
                else {
                    userMapper.selectPage(page,new QueryWrapper<User>().ge("health_status",3).eq("is_locked",1).like("id", user.getId()));//大于等于3
                }
            }
            else {
                if(user.getHealth_status()==0){
                    userMapper.selectPage(page,new QueryWrapper<User>().lt("health_status",3).eq("is_locked",0).like("id", user.getId()));//小于3
                }
                else {
                    userMapper.selectPage(page,new QueryWrapper<User>().ge("health_status",3).eq("is_locked",0).like("id", user.getId()));//大于等于3
                }
            }
        }
        //15全都查
        else if(user.getId() != null && !user.getName().equals("") && user.getIs_locked()!=null && user.getHealth_status()!=null){
            if(user.getIs_locked()==true){
                if(user.getHealth_status()==0){
                    userMapper.selectPage(page,new QueryWrapper<User>().lt("health_status",3).eq("is_locked",1).like("id", user.getId()).like("name",user.getName()));//小于3
                }
                else {
                    userMapper.selectPage(page,new QueryWrapper<User>().ge("health_status",3).eq("is_locked",1).like("id", user.getId()).like("name",user.getName()));//大于等于3
                }
            }
            else {
                if(user.getHealth_status()==0){
                    userMapper.selectPage(page,new QueryWrapper<User>().lt("health_status",3).eq("is_locked",0).like("id", user.getId()).like("name",user.getName()));//小于3
                }
                else {
                    userMapper.selectPage(page,new QueryWrapper<User>().ge("health_status",3).eq("is_locked",0).like("id", user.getId()).like("name",user.getName()));//大于等于3
                }
            }
        }
        //16全都为null
        else userMapper.selectPage(page,null);

        List<User> users = page.getRecords();
        //逆序
        if(sort.equals("-id")) Collections.reverse(users);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("list",users);
        map.put("total",page.getTotal());
        return  map;
    }

    @Override
    public void delete(int id) {
        userMapper.deleteById(id);
    }

    @Override
    public void lock(int id){
        User user = userMapper.selectById(id);
        user.setIs_locked(true);
        userMapper.updateById(user);
    }

    @Override
    public void unLock(int id){
        User user = userMapper.selectById(id);
        user.setIs_locked(false);
        userMapper.updateById(user);
    }

    @Override
    public User getById(int id){
        User user = userMapper.selectById(id);
        return user;
    }

    @Override
    public User getByUserName(String username){

        User user = userMapper.getUserByUserName(username);

        return user;
    }

    @Override
    public User getByOpenid(String openid){

        User user = userMapper.getUserByOpenid(openid);

        return user;
    }

    @Override//信号量  成功:0   两次密码不一致:1   旧密码不正确:2   读写错误:3
    public String ChangePwd(int id,String old_password,String password,String comfirm_password){
        JSONObject result = new JSONObject();
        try {
            User user = userMapper.selectById(id);
            if (!Objects.equals(comfirm_password, password)) {
                result.put("result", 1);
                return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
            }
            if (!Objects.equals(user.getPassword(), old_password)) {
                result.put("result", 2);
                return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
            }
            user.setPassword(password);
            userMapper.updateById(user);
            result.put("result", 0);
            return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
        }catch (Exception e) {
            e.printStackTrace();
            result.put("result", 3);
            return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
        }

    }

    @Override
    public String CancelBind(int id){
        JSONObject result = new JSONObject();
        try {
            User user = userMapper.selectById(id);
            user.setOpenid("");
            userMapper.updateById(user);
            result.put("result",0);
            return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
        }catch (Exception e) {
            e.printStackTrace();
            result.put("result",1);
            return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
        }
    }

    @Override
    public String Bind(int id,String openid){
        JSONObject result = new JSONObject();
        try {
            User user = userMapper.selectById(id);
            user.setOpenid(openid);
            userMapper.updateById(user);
            result.put("result",0);
            return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
        }catch (Exception e) {
            e.printStackTrace();
            result.put("result",1);
            return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
        }
    }

    @Override
    public String IfBind(int id){
        JSONObject result = new JSONObject();
        User user = userMapper.selectById(id);
        if(user.getOpenid()==null || user.getOpenid().equals("")){
            result.put("is_bind",false);
            return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
        }
        else {
            result.put("is_bind",true);
            return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
        }
    }

    @Override//信号量  成功:0   读写错误:1
    public String Edit(int id,int sex,String identity_card,String house_no,String photo){
        JSONObject result = new JSONObject();
        try {
            User user = userMapper.selectById(id);
            user.setSex(sex);
            user.setIdentity_card(identity_card);
            user.setHouse_no(house_no);
            user.setPhoto(photo);
            userMapper.updateById(user);
            result.put("result", 0);
        }catch (Exception e) {
            e.printStackTrace();
            result.put("result",1);
        }
        return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
    }



}
