package com.springboot.final_project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.final_project.Entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {


    @Select("select * from user where username=#{username}")
    User getUserByUserName(String username);

    @Select("select * from user where openid=#{openid}")
    User getUserByOpenid(String openid);
}
