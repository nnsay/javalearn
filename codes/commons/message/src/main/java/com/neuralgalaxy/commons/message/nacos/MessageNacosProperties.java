package com.neuralgalaxy.commons.message.nacos;

import com.alibaba.nacos.api.common.Constants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * nacos配置中心获取消息信息配置
 *
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220227
 */
@Data
@ConfigurationProperties(MessageNacosProperties.PREFIX)
public class MessageNacosProperties {
    public static final String PREFIX = "message.nacos";

    /**
     * 是否启用，默认启用
     */
    boolean enabled = true;

    /**
     * 数据ID
     */
    String dataId = "message-i18n.properties";

    /**
     * 数据组
     */
    String group = Constants.DEFAULT_GROUP;
}
