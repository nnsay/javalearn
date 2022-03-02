package com.neuralgalaxy.stars.auth.model;

import com.neuralgalaxy.commons.visitor.Visitor;
import lombok.Data;

/**
 * 用户token存储的信息
 *
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220223
 */
@Data
public class UserTokenModel implements Visitor {

    /**
     * 用户信息
     */
    long id;

    /**
     * 组织ID
     */
    long orgId;
}
