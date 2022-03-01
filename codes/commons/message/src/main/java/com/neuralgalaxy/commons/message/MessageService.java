package com.neuralgalaxy.commons.message;

import java.util.Locale;

/**
 * 消息覆盖内容获取
 *
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220227
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
