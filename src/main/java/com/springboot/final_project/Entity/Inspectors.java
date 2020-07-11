package com.springboot.final_project.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Inspectors {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    //用户openid
    private String openid;

    //用户名
    private String username;

    //密码
    private String password;

    //用户姓名
    private String name;
}
