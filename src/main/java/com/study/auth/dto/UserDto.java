package com.study.auth.dto;

import com.study.auth.setEnum.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
public class UserDto {
    private String username;
    private String password;
    private String phoneNumber;
    private String email;
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
