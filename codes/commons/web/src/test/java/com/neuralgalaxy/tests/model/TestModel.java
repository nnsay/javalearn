package com.neuralgalaxy.tests.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import java.util.List;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220222
 */
@Data
public class TestModel {

    String name;

    @JsonIgnore
    String password;

    @JsonProperty("nianling")
    Integer age;

    List<Integer> members;
}
