package com.zephyr.windchat.connector;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;

/**
 * 소켓 생성 팩토리 클래스 application.properties에서 서버 정보를 받아온 후 소켓 서버 실행
 * 
 * @author zephyr
 *
 */
@Configuration
public class SocketServerFactory {

    @Value("${socket.host}")
    private String host;

    @Value("${socket.port}")
    private int port;

    @Bean
    public SocketIOServer ioServer() throws InterruptedException {
        // 소켓 서버 설정
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setReuseAddress(true);

        // 일반 설정
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname(host);
        config.setPort(port);
        config.setSocketConfig(socketConfig);
        // 소켓 서버 생성
        SocketIOServer socketIOServer = new SocketIOServer(config);
        // 서버 시작
        socketIOServer.start();
        return socketIOServer;

    }
}
