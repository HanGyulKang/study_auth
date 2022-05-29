package com.study.auth.controller;

import com.study.auth.entity.User;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class IndexController {

    /** =====================================================
     * [TEST]
     ===================================================== */
    @GetMapping("/index")
    public Mono<String> index() {
        return Mono.just("index");
    }


    /** =====================================================
     * [ROLE URL]
     ===================================================== */
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
}
