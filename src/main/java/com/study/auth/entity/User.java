package com.study.auth.entity;

import com.study.auth.setEnum.UserRole;
import com.study.auth.setEnum.UserSocialType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String phoneNumber;
    private String email;
    private Boolean deleted;
//    @Enumerated(EnumType.STRING)
//    private UserRole role;
    private String role;
    @Enumerated(EnumType.STRING)
    private UserSocialType socialType;
    @CreationTimestamp
    private Timestamp createDate;
}
