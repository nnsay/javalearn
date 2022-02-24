package com.neuralgalaxy.stars.users;

import com.neuralgalaxy.commons.asserts.AssertException;
import org.springframework.http.HttpStatus;

/**
 * @author <a href="mailto:ni@renzhen.la">haiker</a>
 * @version 20220223
 */
public interface UserErrors {
    AssertException NOT_FOUND = new AssertException(HttpStatus.NOT_FOUND.value(), "20404", "用户未发现", "Not found user");
}
