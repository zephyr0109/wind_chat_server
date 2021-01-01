package com.zephyr.windchat.domain;


import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@Document
@ToString
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3590340197257074079L;
	
	@Id	
    private String userid;
    @NonNull
    private String password;
    @NonNull
    private String name;
    @NonNull
    private Date birth_day;
    private String authorities;
    private boolean locked;
    private String ip;    
    private Date creation;
    private Date last_access;

    
}
