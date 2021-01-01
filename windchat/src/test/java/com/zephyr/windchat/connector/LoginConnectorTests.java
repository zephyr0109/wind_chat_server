package com.zephyr.windchat.connector;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.zephyr.windchat.etc.SocketEvent;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


class LoginConnectorTests {

	private Socket socket;

	@BeforeEach
	public void init() throws URISyntaxException, InterruptedException {
		socket = IO.socket("http://localhost:8081");
		socket.connect();
		socket.open();	
		Thread.sleep(1000L);
	}

	
	@Test
	public void connectTest() throws UnknownHostException, IOException, URISyntaxException, InterruptedException, JSONException {
		JSONObject obj = new JSONObject();
		obj.put("msg", "test message");		
		System.out.println(obj.toString());
//		socket.emit("test", "\"" + obj.toJSONString() + "\"");
		socket.emit("test", obj);
		System.out.println(socket.connected());
		

	}
	
	@Test
	public void loginTest() throws JSONException {
		JSONObject info = new JSONObject();
		info.put("userId", "zephyr0109");
		info.put("password", "test");		
		socket.once(SocketEvent.LOGIN.getName(), new Emitter.Listener() {
			
			@Override
			public void call(Object... args) {
				// TODO Auto-generated method stub
				for (int i = 0; i < args.length; i++) {
					System.out.println("result : " + args[i]);					
				}
				
			}
		});
		
		socket.emit(SocketEvent.LOGIN.getName(),info );			
	}
}
