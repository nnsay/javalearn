package com.neuralgalaxy.commons.mysql;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import java.util.List;
import java.util.function.Consumer;


/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220223
 */
public interface ExtensionMapper<T> extends BaseMapper<T> {

    default T selectOne(Consumer<LambdaQueryWrapper<T>> consumer) {
        LambdaQueryWrapper<T> wrapper = Wrappers.lambdaQuery();
        consumer.accept(wrapper);
        return selectOne(wrapper);
    }

    default List<T> selectList(Consumer<LambdaQueryWrapper<T>> consumer) {
        LambdaQueryWrapper<T> wrapper = Wrappers.lambdaQuery();
        consumer.accept(wrapper);
        return selectList(wrapper);
    }

    default <P extends IPage<T>> P selectPage(P p, Consumer<LambdaQueryWrapper<T>> consumer) {
        LambdaQueryWrapper<T> wrapper = Wrappers.lambdaQuery();
        consumer.accept(wrapper);
        return selectPage(p, wrapper);
    }
}
