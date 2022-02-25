package com.neuralgalaxy.commons.asserts;

import com.neuralgalaxy.commons.utilities.Locales;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.util.Locale;

/**
 * @author haiker <a href="mailto:wo@renzhen.la">wo@renzhen.la</a>
 * @version 1.0 2019-04-17 11:54
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AssertException extends RuntimeException {
    private int status;

    private String code;
    private String messageZh;
    private String messageUs;

    public AssertException(String code, String messageZh, String messageUs) {
        this(HttpStatus.INTERNAL_SERVER_ERROR.value(), code, messageZh, messageUs);
    }

    public AssertException(int status, String code, String messageZh, String messageUs) {
        super();
        this.status = status;
        this.code = code;
        this.messageZh = messageZh;
        this.messageUs = messageUs;
    }

    @Override
    public String toString() {
        return String.format("[%s]%s", this.getCode(), this.getMessage());
    }

    @Override
    public String getMessage() {
        Locale locale = Locales.mustGet();
        return Locale.CHINA.equals(locale) || Locale.CHINESE.equals(locale) ? this.getMessageZh() : this.getMessageUs();
    }
}
