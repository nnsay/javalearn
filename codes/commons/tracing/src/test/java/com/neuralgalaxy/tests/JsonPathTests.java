package com.neuralgalaxy.tests;

import com.alibaba.fastjson.JSONPath;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220222
 */
public class JsonPathTests {

    @Test
    @SneakyThrows
    public void testJson() {
        InputStream input = JsonPathTests.class.getResourceAsStream("/jsonpath.tests.json");
        String json = new String(IOUtils.readFully(input, input.available()));

        Assertions.assertEquals("test",
                JSONPath.read(json, "$.name"));

        Assertions.assertEquals("",
                JSONPath.read(json, "$.desc"));

        Assertions.assertEquals(0,
                JSONPath.read(json, "$.items[0].index"));

        Assertions.assertEquals("621478283154abaa4019d45f",
                JSONPath.read(json, "$.items[0]._id"));
    }

    @Test
    public void testObject() {
        Map<String, Object> args = new HashMap<>();
        String[] ary = new String[]{"a1", "a2"};
        args.put("name", "test");
        args.put("args", ary);
        args.put("demo", Map.of("k1", "v1", "k2", "v2"));
        args.put("demo2", Map.of("b1", Map.of("l1", "v1")));

        Assertions.assertEquals("test", JSONPath.compile("$.name").eval(args));
        Assertions.assertEquals("a1", JSONPath.compile("$.args[0]").eval(args));
        Assertions.assertEquals("v2", JSONPath.compile("$.demo.k2").eval(args));
        Assertions.assertEquals("v1", JSONPath.compile("$.demo2.b1.l1").eval(args));
    }
}
