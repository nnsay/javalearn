package com.neuralgalaxy.apps.exception;

import org.springframework.http.HttpStatus;

public class RestAPIError extends RuntimeException {
    private String errorCode;
    private String message;
    private HttpStatus status;

    public RestAPIError() {
        super();
    }

    public RestAPIError(String errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    public RestAPIError(HttpStatus status, String errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
        this.status = status;
    }

    public RestAPIError(HttpStatus status, String errorCode, String message) {
        super(errorCode + ":" + message);
        this.errorCode = errorCode;
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
