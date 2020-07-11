package com.springboot.final_project.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component//记得加到容器
public class MybatisObjectHandler implements MetaObjectHandler {

    //插入时的填充出的略
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("star insert fill...");
        //setFieldValByName(String fieldName, Object fieldVal, MetaObject metaObject)
        this.setFieldValByName("time",new Date(),metaObject);
//        this.setFieldValByName("updateTime",new Date(),metaObject);
    }
    //更新时填充
    @Override
    public void updateFill(MetaObject metaObject) {
//        log.info("star update fill...");
//        //setFieldValByName(String fieldName, Object fieldVal, MetaObject metaObject)
//        this.setFieldValByName("updateTime",new Date(),metaObject);
    }
}