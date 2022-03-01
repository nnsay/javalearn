package com.neuralgalxy.tests;

import com.neuralgalaxy.commons.utilities.Copier;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220301
 */
public class CopierTests {

    @Data
    public static class TestBeanModel {
        String name;
    }

    @Data
    public static class TestBeanEntity {
        String name;
    }

    @Test
    public void testCopy() {
        Copier<TestBeanModel, TestBeanEntity> copier = Copier.create(TestBeanModel::new, TestBeanEntity::new);

        TestBeanEntity entity = new TestBeanEntity();
        entity.setName("1231");
        TestBeanModel model = copier.toModel(entity);
        Assertions.assertEquals("1231", model.getName());

        model.setName("changed");
        TestBeanEntity entity1 = copier.toEntity(model);
        Assertions.assertEquals("changed", entity1.getName());
    }
}
