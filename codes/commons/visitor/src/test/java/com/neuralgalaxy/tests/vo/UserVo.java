package com.neuralgalaxy.tests.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neuralgalaxy.commons.visitor.Visitor;
import lombok.Data;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220220
 */
@Data
public class UserVo implements Visitor {

    private long id;
    private String username;

    @JsonIgnore
    private String password;
}
