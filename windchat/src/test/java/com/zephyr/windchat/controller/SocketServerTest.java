package com.zephyr.windchat.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.socket.client.IO;
import io.socket.client.Socket;
import net.minidev.json.JSONObject;


public class SocketServerTest {

	private Socket socket;

	@BeforeAll
	public void init() throws URISyntaxException, InterruptedException {
		socket = IO.socket("http://localhost:8888");
		socket.connect();
		socket.open();
		Thread.sleep(2000L);
	}
	
	
	@Test	
	public void test() {
		System.out.println(socket.connected());
	}
	

	@Test
	public void connectTest() throws UnknownHostException, IOException, URISyntaxException, InterruptedException {		
		JSONObject obj = new JSONObject();
		obj.put("msg", "test message");
		System.out.println(obj.toString());
//		socket.emit("test", "\"" + obj.toJSONString() + "\"");
		socket.emit("test", obj);
		System.out.println(socket.connected());
		Thread.sleep(1000L);
		
	}
}
