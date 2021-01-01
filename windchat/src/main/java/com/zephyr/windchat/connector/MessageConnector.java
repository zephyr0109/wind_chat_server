package com.zephyr.windchat.connector;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.zephyr.windchat.domain.Message;
import com.zephyr.windchat.etc.SocketEvent;
import com.zephyr.windchat.service.MessageService;

/**
 * 메시지 관련 소켓 서버 처리 클래스
 * 
 * @author zephyr
 *
 */
@Component
public class MessageConnector {
    private SocketIOServer socketServer;
    private MessageService messageService;

    @Autowired
    public MessageConnector(SocketIOServer socketServer, MessageService messageService) {
        this.socketServer = socketServer;
        this.messageService = messageService;
        this.socketServer.addListeners(this);
    }

    /**
     * 메시지 전송
     * 
     * @param client    소켓 클라이언트
     * @param msg       메시지 타입 데이터 수신
     * @param ackSender acknowledgement
     */
    @OnEvent(value = "message.send")
    public void send(SocketIOClient client, Message msg, AckRequest ackSender) {
        // TODO : 수신된 데이터에 인증 처리 여부 확인 필요. 프로젝트 분리를 염두에 둬야 함.

        // db에 데이터 추가
        Message savedMsg = messageService.saveMessage(msg);

        Map<String, Object> result = new HashMap<>();
        result.put("data", savedMsg);

        // 해당 방에 데이터 전송
        socketServer.getRoomOperations(msg.getRoomId()).sendEvent(SocketEvent.MESSAGE_RECEIVE.getName(), savedMsg);

        // TODO : 대화방 멤버이나 지금 들어가있지 않은 사용자에게 메시지 전달 필요
        // CASE1 - 멤버이나 join되지 않은 온라인 사용자
        // Case2 - 멤버이고 오프라인인 사용자 - 모바일일 경우 모바일 데이터 전송 필요 - 향후 개발 기능

        // 보낸사람에게 결과 전송
        client.sendEvent(SocketEvent.MESSAGE_SEND.getName(), savedMsg);

    }

}
