package com.neuralgalaxy.apps.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestAPIExceptionHanddler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({RestAPIError.class})
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest request) {

        ex.printStackTrace();
        RestAPIError apiError = (RestAPIError) ex;
        RestError error = new RestError(apiError.getErrorCode());
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("ContentType", "application/json");
        return handleExceptionInternal(ex, error,
                new HttpHeaders(headers), apiError.getStatus(), request);
    }
}
