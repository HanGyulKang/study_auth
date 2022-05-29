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
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String name;
    private String phoneNumber;
    private String email;
    private Boolean deleted;
    private String role;
    @CreationTimestamp
    private Timestamp createDate;
    private String socialType;
    private String socialId;

    @Builder
    public User(String username, String password, String email, String role, String socialType, String socialId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.deleted = false;
        this.socialType = socialType;
        this.socialId = socialId;
    }
}
