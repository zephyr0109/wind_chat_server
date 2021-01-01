package com.zephyr.windchat.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class UtilityBeanFactory {
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {		
		return new BCryptPasswordEncoder();
	}

}
