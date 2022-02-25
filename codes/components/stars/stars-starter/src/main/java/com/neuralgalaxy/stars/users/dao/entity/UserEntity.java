package com.neuralgalaxy.stars.users.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author haiker
 */
@Data
@TableName("Users")
public class UserEntity implements Serializable {
    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户邮箱
     */
    @TableField("email")
    private String email;

    /**
     *
     */
    @TableField("password")
    private String password;

    /**
     *
     */
    @TableField("firstName")
    private String firstName;

    /**
     *
     */
    @TableField("lastName")
    private String lastName;

    /**
     *
     */
    @TableField("createdAt")
    private LocalDateTime createdAt;

    /**
     *
     */
    @TableField("updatedAt")
    private LocalDateTime updatedAt;

    /**
     *
     */
    @TableField("isNGAdmin")
    private Integer isNGAdmin;

    /**
     *
     */
    @TableField("isOrgAdmin")
    private Integer isOrgAdmin;

    /**
     *
     */
    @TableField("orgId")
    private Integer orgId;

    /**
     *
     */
    @TableField("accessToken")
    private String accessToken;

    /**
     *
     */
    @TableField("isActive")
    private Integer isActive;

    /**
     *
     */
    @TableField("accessTokenExpirationTime")
    private LocalDateTime accessTokenExpirationTime;

    /**
     *
     */
    @TableField("isNGRA")
    private Integer isNGRA;

    /**
     *
     */
    @TableField("verifyStatus")
    private Integer verifyStatus;

    /**
     *
     */
    @TableField("lastLoggedAt")
    private LocalDateTime lastLoggedAt;

    /**
     *
     */
    @TableField("username")
    private String username;

    /**
     *
     */
    @TableField("shouldChangePassword")
    private Integer shouldChangePassword;

    /**
     *
     */
    @TableField("localUsername")
    private String localUsername;

    /**
     *
     */
    @TableField("isReviewer")
    private Integer isReviewer;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
