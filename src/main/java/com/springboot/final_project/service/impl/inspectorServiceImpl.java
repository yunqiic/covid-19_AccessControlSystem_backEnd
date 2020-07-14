package com.springboot.final_project.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.final_project.Entity.Admin;
import com.springboot.final_project.Entity.Inspectors;
import com.springboot.final_project.Entity.User;
import com.springboot.final_project.mapper.InspectorsMapper;
import com.springboot.final_project.service.inspectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class inspectorServiceImpl implements inspectorService {

    @Autowired
    private InspectorsMapper inspectorsMapper;

    @Override
    public Map<String,Object> inspectorListById(int id, int index, int limit) {
        Page<Inspectors> page = new Page<>(index,limit);
        inspectorsMapper.selectPage(page,new QueryWrapper<Inspectors>().eq("id",id));
        List<Inspectors> inspectors = page.getRecords();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("list",inspectors);
        map.put("total",page.getTotal());

        return map;
    }

    @Override
    public Map<String,Object> inspectorListByName(String name,int index, int limit) {
        Page<Inspectors> page = new Page<>(index,limit);
        inspectorsMapper.selectPage(page,new QueryWrapper<Inspectors>().like("username",name));
        List<Inspectors> inspectors = page.getRecords();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("list",inspectors);
        map.put("total",page.getTotal());
        return map;
    }

    @Override
    public Map<String,Object> inspectorList(int index, int limit) {

        Page<Inspectors> page = new Page<>(index,limit);
        inspectorsMapper.selectPage(page,null);
        List<Inspectors> inspectors = page.getRecords();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("list",inspectors);
        map.put("total",page.getTotal());
        return  map;

    }

    @Override
    public Map<String,Object> createInspector(Inspectors inspectors) {
        Integer result = 0;
        QueryWrapper<Inspectors> wrapper = new QueryWrapper();
        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            Inspectors inspectors1= inspectorsMapper.selectOne(wrapper.eq("username",inspectors.getUsername()));
            if (inspectors1 != null) {
                result = 1;
                map.put("result",result);
                map.put("id",0);
                return map;
            } else {
                inspectorsMapper.insert(inspectors);
                Inspectors inspectors2 = inspectorsMapper.selectOne(wrapper.eq("username",inspectors.getUsername()));
                result = 0;
                map.put("result",result);
                map.put("id",inspectors2.getId());
                System.out.println("mmmmmmm");
                return map;
            }
        }catch (Exception e) {
            e.printStackTrace();
            result = 3;
            map.put("result",result);
            map.put("id",0);
            return map;
        }
    }

    @Override
    public int updatePassword(Inspectors inspectors) {
        Integer result ;
        try {
            inspectorsMapper.updateById(inspectors);
            result = 0;
        }catch (Exception e) {
            e.printStackTrace();
            result = 3;
        }
        return result;
    }

    @Override
    public int deleteInspector(Inspectors inspectors) {
        Integer result = 0;
        inspectorsMapper.deleteById(inspectors.getId());
        return result;
    }

    @Override
    public int resetOpenid(Inspectors inspectors) {
        Integer result = 0;
        inspectorsMapper.updateById(inspectors);
        return 0;
    }

    @Override
    public String getOpenid(String code) {
        QueryWrapper<Inspectors> wrapper = new QueryWrapper();

        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString("https://api.weixin.qq.com/sns/jscode2session?appid=wx3e5955768b640056&secret=02878d1ff0518b5893a35c4227fce5e0&js_code={code}&grant_type=authorization_code").build()
                .expand(code)
                .encode();
        String WX_URL = uriComponents.toString();

        RequestEntity<Void> requestEntity = RequestEntity
                .get(URI.create(WX_URL))
                .build();
        ResponseEntity<String> exchange = new RestTemplate().exchange(requestEntity, String.class);
        String body = exchange.getBody();
        JSONObject jsonObject = JSON.parseObject(body);
        JSONObject result = new JSONObject();
        String openid = jsonObject.getString("openid");
        System.out.println(openid);
        result.put("openid",openid);
        Inspectors inspectors = inspectorsMapper.selectOne(wrapper.eq("openid", openid));
        if(inspectors==null) result.put("bindSign",false);
        else result.put("bindSign",true);
        return result.toJSONString();
    }

    @Override   //信号量  成功:0   两次密码不一致:1   旧密码不正确:2   读写错误:3
    public String ChangePwd(int id,String old_password,String password,String confirm_password){
        JSONObject result = new JSONObject();
        try {
            Inspectors inspectors = inspectorsMapper.selectById(id);
            if (!Objects.equals(confirm_password, password)) {
                result.put("result", 1);
                return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
            }
            if (!Objects.equals(inspectors.getPassword(), old_password)) {
                result.put("result", 2);
                return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
            }
            inspectors.setPassword(password);
            inspectorsMapper.updateById(inspectors);
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
            Inspectors inspectors = inspectorsMapper.selectById(id);
            inspectors.setOpenid("");
            inspectorsMapper.updateById(inspectors);
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
            Inspectors inspectors = inspectorsMapper.selectById(id);
            inspectors.setOpenid(openid);
            inspectorsMapper.updateById(inspectors);
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
        Inspectors inspectors = inspectorsMapper.selectById(id);
        if(inspectors.getOpenid()==null || inspectors.getOpenid().equals("")){
            result.put("is_bind",false);
            return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
        }
        else {
            result.put("is_bind",true);
            return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
        }
    }
}
