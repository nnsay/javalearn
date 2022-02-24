package com.neuralgalaxy.commons.web.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.support.springfox.SwaggerJsonSerializer;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * webmvc
 *
 * @author haiker
 */
public class FastJsonConfigurer implements WebMvcConfigurer {

    /**
     * 添加fastjson converter
     *
     * @param converters 转换管理器
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();

        //创建配置类
        FastJsonConfig config = new FastJsonConfig();
        //修改配置返回内容的过滤
        config.setSerializerFeatures(
                //消除对同一对象循环引用的问题，默认为false（如果不配置有可能会进入死循环）
                SerializerFeature.DisableCircularReferenceDetect,
                //List字段如果为null,输出为[],而非null
                SerializerFeature.WriteNullListAsEmpty,
                //Boolean字段如果为null,输出为false,而非null
                SerializerFeature.WriteNullBooleanAsFalse

        );
        config.getSerializeConfig().put(JSON.class, new SwaggerJsonSerializer());
        config.getSerializeConfig().put(String.class, OverrideToStringSerializer.instance);
        converter.setFastJsonConfig(config);

        converters.add(0, converter);
    }
}
