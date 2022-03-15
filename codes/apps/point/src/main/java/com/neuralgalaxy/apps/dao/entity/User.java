package com.neuralgalaxy.apps.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("Users")
public class User implements Serializable {
    @TableId(type= IdType.AUTO)
    private Integer id;

    @TableField("username")
    private String accountName;

    @TableField("email")
    private String email;

    @TableField("isActive")
    private Boolean isActive;

    @TableField("createdAt")
    private LocalDateTime createdAt;
}
