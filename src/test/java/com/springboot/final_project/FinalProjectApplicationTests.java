package com.springboot.final_project;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.final_project.Entity.Admin;
import com.springboot.final_project.Entity.User;
import com.springboot.final_project.mapper.AdminMapper;
import com.springboot.final_project.mapper.RecordFormMapper;
import com.springboot.final_project.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.SocketTimeoutException;
import java.util.List;

@SpringBootTest
class FinalProjectApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private RecordFormMapper recordFormMapper;

    @Test
    void contextLoads() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        for(User user:userList) {
            System.out.println(user);
        }
//        Admin admin = new Admin();
//        admin.setUsername("1234");
//        admin.setPassword("123");
//        admin.builder().username("123").password("123").build();
//        adminMapper.insert(admin);
        Page<Admin> page = new Page<>(1,3);
        adminMapper.selectPage(page,null);
        System.out.println("===============================");
        System.out.println(page.getRecords());

//        RecordForm recordForm = new RecordForm();
//        recordForm.setInspectors_id(1);
//        recordForm.setTemperature(38.0);
//        recordForm.setUser_id(1);
//        recordFormMapper.insert(recordForm);


    }



}
