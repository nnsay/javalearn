package com.neuralgalaxy.commons.web.resolver;

import java.util.Locale;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220225
 */
public interface MessageService {

    /**
     * 获取覆盖消息
     *
     * @param locale 地区信息
     * @param code   错误码
     * @return 如果返回为空消息不覆盖
     */
    String getOverwriteMessage(Locale locale, String code);
}
