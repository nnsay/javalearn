package com.neuralgalaxy.commons.asserts;

import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * @author haiker <a href="mailto:wo@renzhen.la">wo@renzhen.la</a>
 * @version 1.0 2019-04-17 11:53
 */
public class Asserts {

    public static void notNull(Object o, AssertException code) {
        if (o == null) {
            throw code;
        } else if (o instanceof Optional && ((Optional<?>) o).isEmpty()) {
            throw code;
        }
    }

    public static void hasText(String o, AssertException code) {
        isTrue(StringUtils.hasText(o), code);
    }

    public static void notEmpty(String str, AssertException code) {
        if (StringUtils.isEmpty(str)) {
            throw code;
        }
    }

    public static void isTrue(boolean check, AssertException code) {
        if (!check) {
            throw code;
        }
    }

}
