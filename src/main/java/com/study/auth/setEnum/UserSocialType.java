package com.study.auth.setEnum;

public enum UserSocialType {
    KAKAO("KAKAO"), APPLE("APPLE"), GOOGLE("GOOGLE");

    private final String socialType;

    UserSocialType(String socialType) {
        this.socialType = socialType;
    }

    public String getSocialType() {
        return socialType;
    }
}
