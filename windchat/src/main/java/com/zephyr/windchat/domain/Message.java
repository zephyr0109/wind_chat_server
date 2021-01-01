package com.zephyr.windchat.domain;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;

import com.zephyr.windchat.etc.JSONUtil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message{

	@Id
	private String id;
		
	private String roomId;
	
	private String writer;

	private String msg;
	
    private LocalDate writeTime;
	
	public Message(String resource) {
		JSONUtil.callGetterFromJSONString(this, resource);
	}

	
		
}
