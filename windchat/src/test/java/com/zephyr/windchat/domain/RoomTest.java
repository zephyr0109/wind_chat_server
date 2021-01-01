package com.zephyr.windchat.domain;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class RoomTest {

	@Test
	void test() {
		
	}
	
	@Test
	void createTest() {
		Room room  = new Room();
		room.setRoomId("test");
		assertNotNull(room.getRoomId());		
		
	}

}
