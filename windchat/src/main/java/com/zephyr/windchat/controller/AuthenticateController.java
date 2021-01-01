package com.zephyr.windchat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zephyr.windchat.domain.LoginDto;
import com.zephyr.windchat.domain.User;
import com.zephyr.windchat.service.UserService;

/**
 * web 접속 시 사용할 로그인 처리 컨트롤러 클래스만 만들어 둔 상태이며 후에 기능 추가 예정.
 * 
 * @author zephyr
 *
 */
@RestController(value = "user")
public class AuthenticateController {
    private UserService userService;

    @Autowired
    public AuthenticateController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User login(@RequestBody LoginDto loginInfo) {
        // TODO 유효성 처리 필요
        User user = userService.getUser(loginInfo).block();
        if (user != null) {
            // TODO : 로그인 정보 외에 인증 토근 생성 후 전달 필요
            return user;
        } else {
            // TODO 에러 발생 시 적절한 response를 전달해야함
            return null;
        }
    }
}
