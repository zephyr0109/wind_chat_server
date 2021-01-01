package com.zephyr.windchat.service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zephyr.windchat.domain.Message;
import com.zephyr.windchat.domain.MessageRepository;

import reactor.core.publisher.Mono;

/**
 * 메시지 전송 관련 서비스 TODO (서비스 공통) block 후 일반 타입으로 변경해야 할지 아니면 mono, flux 형태로 전달하는게 더 좋은지
 * 확인 필요
 * 
 * @author zephyr
 *
 */
@Service
public class MessageService {

	private MessageRepository messageRepository;

	@Autowired
	public MessageService(MessageRepository messageRepository) {
		// TODO Auto-generated constructor stub
		this.messageRepository = messageRepository;
	}

    // 메시지 전송
	public Message saveMessage(Message message) {
		message.setId(UUID.randomUUID().toString());
        message.setWriteTime(LocalDate.now());
		Mono<Message> messageMono = messageRepository.save(message);		
		if (messageMono != null) {			
			return messageMono.block(Duration.ofMillis(1000L));
		} else {
			return null;
		}

	}

    // 대화방에 대한 모든 메시지를 조회
	public List<Message> findMessageByRoomId(String roomId) {
        // TODO 메시지가 많아질 수록 전체 데이터를 가져올 수 없으므로 기간도 추가할 필요가 있음.
        List<Message> msgList = messageRepository.findByRoomId(roomId).collectList().block();
		return msgList;
	}
	
}
