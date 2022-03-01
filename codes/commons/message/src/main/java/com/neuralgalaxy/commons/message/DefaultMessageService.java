package com.neuralgalaxy.commons.message;

import lombok.Data;

import java.util.Locale;
import java.util.Properties;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220227
 */
@Data
public class DefaultMessageService implements MessageService {

    protected String profix;
    protected Properties cache;

    @Override
    public String getOverwriteMessage(Locale locale, String code) {
        if (cache == null) {
            return null;
        }
        String key = String.format("%s.%s.%s", this.profix, locale.getLanguage(), code);
        return cache.getProperty(key);
    }
}
