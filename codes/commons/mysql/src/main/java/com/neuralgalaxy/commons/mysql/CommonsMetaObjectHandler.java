package com.neuralgalaxy.commons.mysql;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * createTime,updateTime设置自动设置处理
 * @author haiker
 */
@Slf4j
public class CommonsMetaObjectHandler implements MetaObjectHandler {

    /**
     * createTime 自动处理
     * @param metaObject do类
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.strictInsertFill(metaObject, "createdAt", LocalDateTime::now, LocalDateTime.class);
    }

    /**
     * updateTime 自动处理
     * @param metaObject do
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictUpdateFill(metaObject, "updatedAt", LocalDateTime::now, LocalDateTime.class);
    }
}
