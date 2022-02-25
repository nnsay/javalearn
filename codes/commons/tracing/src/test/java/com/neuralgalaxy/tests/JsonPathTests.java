package com.neuralgalaxy.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220222
 */
public class JsonPathTests {


    @Test
    @SneakyThrows
    public void testObject() {
        Map<String, Object> args = new HashMap<>();
        String[] ary = new String[]{"a1", "a2"};
        args.put("name", "test");
        args.put("args", ary);
        args.put("demo", Map.of("k1", "v1", "k2", "v2"));
        args.put("demo2", Map.of("b1", Map.of("l1", "v1")));

        String json = new ObjectMapper().writeValueAsString(args);

        Assertions.assertEquals("test", JsonPath.compile("$.name").read(json));
        Assertions.assertEquals("a1", JsonPath.compile("$.args[0]").read(json));
        Assertions.assertEquals("v2", JsonPath.compile("$.demo.k2").read(json));
        Assertions.assertEquals("v1", JsonPath.compile("$.demo2.b1.l1").read(json));
    }
}
