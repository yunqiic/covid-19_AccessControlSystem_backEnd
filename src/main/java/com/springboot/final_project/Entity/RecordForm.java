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
    @TableField(fill = FieldFill.INSERT)
    private Date time;

    private Integer user_id;

    private Integer inspectors_id;

    private User user;

    private Inspectors inspectors;
}
