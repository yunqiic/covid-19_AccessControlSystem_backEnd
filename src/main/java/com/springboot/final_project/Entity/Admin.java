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
public class Admin {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    //用户名
    private String username;

    //密码
    private String password;
}
