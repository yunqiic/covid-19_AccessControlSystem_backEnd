package com.springboot.final_project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.final_project.Entity.Admin;
import com.springboot.final_project.Entity.Inspectors;
import com.springboot.final_project.mapper.InspectorsMapper;
import com.springboot.final_project.service.inspectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public int createInspector(Inspectors inspectors) {
        Integer result = 0;
        QueryWrapper<Inspectors> wrapper = new QueryWrapper();

        Inspectors inspectors1= inspectorsMapper.selectOne(wrapper.eq("username",inspectors.getUsername()));

        if(inspectors1 != null){
            result =1;
            return result;
        }else {
            inspectorsMapper.insert(inspectors);
            result=0;
            return result;
        }
    }

    @Override
    public int updatePassword(Inspectors inspectors) {
        Integer result = 1;
        inspectorsMapper.updateById(inspectors);
        result=0;
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

}
