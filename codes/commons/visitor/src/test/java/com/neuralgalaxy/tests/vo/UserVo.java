package com.neuralgalaxy.tests.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.neuralgalaxy.commons.visitor.Visitor;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220220
 */

public class UserVo implements Visitor {
    private long userId;
    private String username;

    @JSONField(serialize = false)
    private String password;

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public long getId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
