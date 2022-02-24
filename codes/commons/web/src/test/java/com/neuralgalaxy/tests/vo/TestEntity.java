package com.neuralgalaxy.tests.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220222
 */
@Data
public class TestEntity {

    String name;

    @JSONField(serialize = false)
    String password;

    @JSONField(name = "nianling")
    Integer age;

    List<Integer> members;
}
