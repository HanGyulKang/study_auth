package com.study.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/auth")
public class IndexController {

    @GetMapping("/index")
    public Mono<String> index() {
        return Mono.just("index");
    }

    @GetMapping("/user")
    public Mono<String> user() {
        return Mono.just("user");
    }

    @GetMapping("/admin")
    public Mono<String> admin() {
        return Mono.just("admin");
    }

    @GetMapping("/manager")
    public Mono<String> manager() {
        return Mono.just("manager");
    }

    @GetMapping("/login")
    public Mono<String> login() {
        return Mono.just("login");
    }

    @GetMapping("/join")
    public Mono<String> join() {
        return Mono.just("join");
    }

    @GetMapping("/joinProc")
    public Mono<String> joinProc() {
        return Mono.just("회원가입 완료");
    }
}
