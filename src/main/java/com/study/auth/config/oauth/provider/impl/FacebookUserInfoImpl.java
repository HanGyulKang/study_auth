package com.study.auth.config.oauth.provider.impl;

import com.study.auth.config.oauth.provider.OAuth2UserInfo;

import java.util.Map;

public class FacebookUserInfoImpl implements OAuth2UserInfo {

    private Map<String, Object> attributes; // oauth2User.etAttributes()

    public FacebookUserInfoImpl(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getSocialId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getSocialType() {
        return "facebook";
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }
}
