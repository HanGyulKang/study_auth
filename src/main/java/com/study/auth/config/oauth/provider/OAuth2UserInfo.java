package com.study.auth.config.oauth.provider;

public interface OAuth2UserInfo {
    String getSocialId();
    String getSocialType();
    String getEmail();
    String getName();
}
