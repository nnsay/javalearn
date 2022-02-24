package com.neuralgalaxy.commons.utilities;

import java.util.Locale;
import java.util.Optional;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220219
 */
public class Locales {
    /**
     * 获取语言
     */
    public static Optional<Locale> get() {
        try {
            return Optional.of(Requests.get().getLocale());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static Locale mustGet() {
        return get().orElse(Locale.getDefault());
    }
}
