package com.springboot.final_project.Entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordForm {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    //体温
    private Double temperature;

    //创建时间
//    @TableField(fill = FieldFill.INSERT)
    private Date time;

    private Integer user_id;

    private Integer inspectors_id;

//    @TableField(exist = false)//表示该属性不为数据库表字段，但又是必须使用的
//    private User user;
//
//    @TableField(exist = false)//表示该属性不为数据库表字段，但又是必须使用的
//    private Inspectors inspectors;
}
