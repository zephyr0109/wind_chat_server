package com.zephyr.windchat.domain;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.zephyr.windchat.etc.JSONUtil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {
    // TODO : DTO의 필요성이 보임. Room의 정보 중 DTO로 분리할 데이터를 선정할 것

    @Id
    private String roomId;

    private Date createTime;
    private String name;
    private List<String> users;
    private String owner;

    private List<Message> msgs;

    public Room(String resource) {
        JSONUtil.callGetterFromJSONString(this, resource);
    }

    public Room(Date createTime, List<String> users, String owner) {
        this.createTime = createTime;
        this.users = users;
        this.owner = owner;
    }
}
