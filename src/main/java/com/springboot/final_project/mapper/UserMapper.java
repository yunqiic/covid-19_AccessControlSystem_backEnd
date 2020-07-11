package com.springboot.final_project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.final_project.Entity.User;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    List<User> getUserList(Page<User> page);
}
