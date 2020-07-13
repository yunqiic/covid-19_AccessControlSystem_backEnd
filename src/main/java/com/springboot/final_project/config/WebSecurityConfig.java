package com.springboot.final_project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MyAuthenctiationSuccessHandler myAuthenctiationSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/","/waterCheck").permitAll() // permitAll被允许访问
                .anyRequest().authenticated() // 其余的请求需要认证后才可允许访问
                .and()
                .formLogin()
                .successHandler(myAuthenctiationSuccessHandler)
//                .loginPage("/")
//                .loginProcessingUrl("/admin/login")
                .and()
                .logout()
                .permitAll()
                .and()
                .csrf().disable(); ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication() // 在内存中进行身份验证
                .withUser("222")
                .password("222")
                .roles("USER");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/css/**", "/js/**");
    }

}
