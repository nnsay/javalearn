package com.neuralgalaxy.tests;

import com.neuralgalaxy.tests.vo.TestEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220225
 */
public class TeMapTests {

    @Test
    public void testTEst() {
        TestEntity test = new TestEntity();
        test.setAge(12);
        test.setName("name");
        Map<String,Object> p = new HashMap<>();
        BeanUtils.copyProperties(test,p);
        System.out.println(p);
    }

}
