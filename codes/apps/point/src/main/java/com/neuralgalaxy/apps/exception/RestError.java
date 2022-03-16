package com.neuralgalaxy.apps.exception;

public class RestError {
    private String serverName = "POINT";
    private String errorCode;

    public RestError(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getServerName() {
        return serverName;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
