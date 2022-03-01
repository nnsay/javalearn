package com.neuralgalaxy.commons.message.nacos;

import com.neuralgalaxy.commons.message.DefaultMessageService;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220227
 */
public class NacosMessageService extends DefaultMessageService {

    public void setMessageString(String message) throws IOException {
        if(null == message){
            return;
        }
        Properties properties = new Properties();
        properties.load(new StringReader(message));
        this.setCache(properties);
    }
}
