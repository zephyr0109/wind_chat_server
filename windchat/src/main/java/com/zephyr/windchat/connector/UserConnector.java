package com.zephyr.windchat.connector;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.zephyr.windchat.domain.LoginDto;
import com.zephyr.windchat.domain.User;
import com.zephyr.windchat.etc.SocketEvent;
import com.zephyr.windchat.service.UserService;

/**
 * 사용자 인증 처리용 소켓 서버 일종의 인증 서버 역할
 * 
 * @author zephyr
 *
 */
@Component
public class UserConnector {

    private SocketIOServer socketServer;

    private UserService service;

    @Autowired
    public UserConnector(SocketIOServer socketServer, UserService service) {
        this.socketServer = socketServer;
        this.service = service;
        this.socketServer.addListeners(this);
    }

    /**
     * 로그인 처리
     * 
     * @param client
     * @param loginInfo
     * @param ackSender
     */
    @OnEvent("login")
    public void login(SocketIOClient client, LoginDto loginInfo, AckRequest ackSender) {
        // TODO 유효성 처리 필요
        User user = service.getUser(loginInfo).block();

        if (user != null && service.checkPwd(loginInfo, user)) {
            // TODO : 로그인 정보 외에 인증 토근 생성 후 전달 필요
            client.sendEvent("login", user);
            client.set("userInfo", user);
        } else if (user == null) {
            Map<String, Object> errObj = new HashMap<>();
            errObj.put("err", true);
            errObj.put("code", "E_USER_NOT_EXISTS");
            // client.sendEvent("login", err);
            client.sendEvent("login", errObj);
        }
    }

    /**
     * 사용자 추가
     * 
     * @param client
     * @param data
     * @param ackSender
     */
    @OnEvent(value = "user.register")
    public void register(SocketIOClient client, User data, AckRequest ackSender) {
        // TODO 사용자 추가 후 결과 전달
        User savedUser = service.registerUser(data).block();
        client.sendEvent(SocketEvent.USER_REGISTER.getName(), savedUser);
    }

}
