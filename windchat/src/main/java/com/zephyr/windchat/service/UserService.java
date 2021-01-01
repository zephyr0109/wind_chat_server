package com.zephyr.windchat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zephyr.windchat.domain.LoginDto;
import com.zephyr.windchat.domain.User;
import com.zephyr.windchat.domain.UserRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    // 비밀번호 확인, 생성용 객체
    private final PasswordEncoder encoder;

    public UserService() {
        this.userRepository = null;
        this.encoder = null;
    }

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;

    }

    // 사용자 정보 조회
    public Mono<User> getUser(LoginDto data) {
        Mono<User> userMono = userRepository.findById(data.getUserId());
        return userMono;
    }

    // 비밀번호 확인
    public boolean checkPwd(LoginDto data, User user) {
        return encoder.matches(data.getPassword(), user.getPassword());
    }

    // 사용자 추가
    public Mono<User> registerUser(User data) {

        // TODO : 유효성 처리 필요
        String encodedPassword = encoder.encode(data.getPassword());

        data.setPassword(encodedPassword);
        Mono<User> userMono = userRepository.save(data);
        return userMono;
    }
}
