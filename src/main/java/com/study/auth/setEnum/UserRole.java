package com.study.auth.setEnum;

public enum UserRole {
    USER("USER"), ADMIN("ADMIN"), MANAGER("MANAGER");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}

