package com.neuralgalaxy.stars.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220225
 */
@Data
@ApiModel(description = "用户模型")
public class UserModel implements Serializable {

    private Integer id;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private Integer isOrgAdmin;
    private Integer orgId;
    private String accessToken;
    private Integer isActive;
    private LocalDateTime accessTokenExpirationTime;
    private Integer isNGRA;
    private Integer verifyStatus;
    private LocalDateTime lastLoggedAt;
    private Integer shouldChangePassword;
    private String localUsername;
    private Integer isReviewer;
    private Integer isNGAdmin;

    @JsonIgnore
    private String password;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
