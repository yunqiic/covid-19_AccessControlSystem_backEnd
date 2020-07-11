package com.springboot.final_project.Entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

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

    //用户性别
    private Integer sex;

    //用户身份证
    private String identity_card;

    //用户相片
    private String photo;

    //房号
    private String house_no;

    //健康状态
    private Boolean health_status;

    //出入次数
    private Boolean access_times;

    //账号是否被锁定
    private Boolean is_locked;

    @TableField(exist = false)//表示该属性不为数据库表字段，但又是必须使用的
    private List<RecordForm> recordFormList;

}
