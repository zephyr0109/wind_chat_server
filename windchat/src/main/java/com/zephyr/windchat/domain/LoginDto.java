package com.zephyr.windchat.domain;

import com.zephyr.windchat.etc.JSONUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginDto {

	private String userId;

	private String password;

	public LoginDto(String resource) {
		JSONUtil.callGetterFromJSONString(this, resource);
	}
	
}
