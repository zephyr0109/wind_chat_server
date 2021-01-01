package com.zephyr.windchat.domain;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;


class RoomRepositoryTest {

	@MockBean
	RoomRepository repository;
	
	@BeforeEach
	void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	void saveRoomTest() {
		Room room = Room.builder().build();		
		verify(repository).save(any());
		
	}

}
