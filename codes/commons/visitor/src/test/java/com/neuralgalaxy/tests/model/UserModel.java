package com.neuralgalaxy.tests.model;

import com.neuralgalaxy.commons.visitor.Visitor;
import lombok.Data;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220228
 */
@Data
public class UserModel implements Visitor {
    private long id;
    String username;
    String passwordd;
}
