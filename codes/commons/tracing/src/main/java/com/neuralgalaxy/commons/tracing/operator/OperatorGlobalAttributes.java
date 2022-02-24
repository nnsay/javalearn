package com.neuralgalaxy.commons.tracing.operator;

import java.util.Map;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220222
 */
public interface OperatorGlobalAttributes {

    /**
     * 获取全局参数，例如用户ID等。
     *
     * @return 全局参数
     */
    Map<String, Object> getGlobalArgs();
}
