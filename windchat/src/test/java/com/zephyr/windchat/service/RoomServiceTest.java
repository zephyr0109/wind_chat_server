package com.zephyr.windchat.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.zephyr.windchat.domain.MessageRepository;
import com.zephyr.windchat.domain.Room;
import com.zephyr.windchat.domain.RoomRepository;

class RoomServiceTest {

	private RoomService roomService;

	@Mock
	private RoomRepository roomRepository;
	
	@Mock
	private MessageRepository messageRepository;

	@BeforeEach
	void init() {
		MockitoAnnotations.initMocks(this);
		roomService = new RoomService(roomRepository, messageRepository);
	}

//	방 생성 테스트
	@Test
	void createRoomTest() {
		Room room = Room.builder().build();
		System.out.println(roomService);
		roomService.createRoom(room);
		verify(roomRepository).save(any());
	}

//	방 삭제 테스트
	@Test
	void deleteRoomTest() {		
		

	}

//	방 이름 수정 테스트
	@Test
	void updateRoomNameTest() {

	

	}

//	방 초대 테스트	
	void addUserInRoomTest() {

	}

// 자기가 속한 방 조회
	@Test
	void findRoomByUserId() {
		String userId = "star8076";
		List<Room> roomList = roomService.findRoomByUserId(userId);

		List<String> userIdList = new ArrayList<>();
		userIdList.add(userId);
		verify(roomRepository).findByOwnerOrUsers(userId, userIdList);

	}

}
