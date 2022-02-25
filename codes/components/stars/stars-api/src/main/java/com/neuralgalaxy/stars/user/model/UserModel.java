package com.neuralgalaxy.stars.user.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220225
 */
@Data
public class UserModel  {
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
