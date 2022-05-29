package com.study.auth.repository;

import com.study.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // findBy 까지는 규칙 -> Username 은 문법
    // => select u from user u where username = :username;
    public User findByUsername(String username);
}
