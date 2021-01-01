package com.zephyr.windchat.domain;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
	
	Mono<User> findUserByUseridAndPassword(String userid, String password);
	
}
