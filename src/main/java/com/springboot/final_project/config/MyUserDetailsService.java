package com.springboot.final_project.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.springboot.final_project.Entity.Admin;
import com.springboot.final_project.mapper.AdminMapper;
import com.springboot.final_project.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Component
@Service
public class MyUserDetailsService implements UserDetailsService {

//    @Autowired
//    UserMapper userMapper;

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        com.springboot.final_project.Entity.User  myUser = userMapper.selectOne(new QueryWrapper<com.springboot.final_project.Entity.User>().eq("username",username));

        Admin admin = adminMapper.selectOne(new QueryWrapper<Admin>().eq("username",username));

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        // 封装用户信息，并返回。参数分别是：用户名，密码，用户权限
        User user = new User(username, passwordEncoder.encode(admin.getPassword()),
                authorities);

        return user;
    }
}