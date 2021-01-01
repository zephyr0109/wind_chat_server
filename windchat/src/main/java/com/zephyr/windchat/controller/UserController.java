package com.zephyr.windchat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zephyr.windchat.domain.User;
import com.zephyr.windchat.service.UserService;

import reactor.core.publisher.Mono;

/**
 * 회원가입, 회원정보 수정, 회원 조회 등 사용자 관리 컨트롤러
 * 
 * @author zephyr
 *
 */
@RestController(value = "user")
public class UserController {

    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public String welcome() {
        return "{name : test}";
    }

    /**
     * 회원 가입
     * 
     * @param user
     * @return
     */
    @PostMapping
    public Mono<User> register(@RequestBody User user) {
        return service.registerUser(user);

    }

}
