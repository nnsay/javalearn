package com.neuralgalaxy.commons.message;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * nacos配置中心获取消息信息配置
 *
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220227
 */
@Data
@ConfigurationProperties(MessageProperties.PREFIX)
public class MessageProperties {

    public static final String PREFIX = "message";

    /**
     * 前缀信息，为了保证信息的
     */
    String prefix = "i18n";
}
