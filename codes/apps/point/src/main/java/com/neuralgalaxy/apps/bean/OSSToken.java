package com.neuralgalaxy.apps.bean;

import org.springframework.stereotype.Component;


public class OSSToken {
    private String accessKey;
    private String accessSecret;
    private String sessionSecurity;

    public OSSToken(String accessKey, String accessSecret, String sessionSecurity) {
        this.accessKey = accessKey;
        this.accessSecret = accessSecret;
        this.sessionSecurity = sessionSecurity;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }

    public void setSessionSecurity(String sessionSecurity) {
        this.sessionSecurity = sessionSecurity;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public String getSessionSecurity() {
        return sessionSecurity;
    }
}
