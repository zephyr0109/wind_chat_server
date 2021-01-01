package com.zephyr.windchat.domain;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Flux;

@Repository
public interface MessageRepository extends ReactiveMongoRepository<Message, ObjectId>{
	
	Flux<Message> findByRoomId(String roomId);	
	
}
