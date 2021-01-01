package com.zephyr.windchat.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.zephyr.windchat.domain.Message;
import com.zephyr.windchat.domain.MessageRepository;

class MessageServiceTest {

	private MessageService messageService;

	@Mock
	private MessageRepository messageRepository;

	@BeforeEach
	void init() {
		MockitoAnnotations.initMocks(this);
		messageService = new MessageService(messageRepository);

	}

//	메시지 추가
	@Test
	void saveMessage() {
		Message message = new Message();
		Message savedMsg = messageService.saveMessage(message);

		verify(messageRepository).save(any());
		assertThat(message.getMsg()).isEqualTo(savedMsg.getMsg());
	}
	
//	방 내에 있는 모든 메시지를 조회
	@Test
	void findMessageByRoomId () {
//		List<Message> msgList = messageService.findMessageByRoomId(new ObjectId("5f46029fead5c71c954ddd96"));
		
	}
	
//	메시지 
	
	

}
