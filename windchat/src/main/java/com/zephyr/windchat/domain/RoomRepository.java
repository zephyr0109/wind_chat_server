package com.zephyr.windchat.domain;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;

@Repository
public interface RoomRepository extends ReactiveMongoRepository<Room, String>{

	
	Flux<Room> findByOwnerOrUsers(String owner, List<String> users);
	
	@Query("{'users':{'$in':?0}}")
	Flux<Room> findByUsersInUser(List<String> users);
	
	
	Flux<Room> findByUsers(String userId);
	
}
