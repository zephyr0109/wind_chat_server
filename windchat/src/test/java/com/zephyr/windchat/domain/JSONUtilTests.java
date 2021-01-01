package com.zephyr.windchat.domain;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;


class JSONUtilTests {

	@Test
	void JSONConvertTest() throws JSONException {
		List<String> users = new ArrayList<>();
		users.add("zephyr");
		users.add("ccw");
		Room room = new Room();
		room.setName("test room");
		room.setOwner("zephyr");

		room.setUsers(users);
		//room.setCreateTime(new Date());
//		JSONObject obj = JSONUtil.convertPojoToJSONObject(room);
//		System.out.println(obj);		
//		Room rebuildRoom = new Room(obj.toString());
		
		JSONObject obj2 = new JSONObject();
		JSONArray userArray = new JSONArray(users);
		obj2.put("name", "test room");
		obj2.put("users", userArray);
		Room room2 = new Room(obj2.toString());
		System.out.println(room2);
		
		
		
	}

}
