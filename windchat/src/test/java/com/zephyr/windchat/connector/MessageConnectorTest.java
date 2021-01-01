package com.zephyr.windchat.connector;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URISyntaxException;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.zephyr.windchat.etc.SocketEvent;

import io.socket.client.IO;
import io.socket.client.Socket;

@SpringBootTest
class MessageConnectorTest {
	private Socket socket;

	@BeforeEach
	public void init() throws URISyntaxException, InterruptedException {
		socket = IO.socket("http://localhost:8081");
		socket.connect();
		waitConnect(socket, 1000L, 10);	 	
		JSONObject obj = new JSONObject();
		obj.put("userId", "star8076");
		obj.put("roomId", "5f46029fead5c71c954ddd96");		
		socket.emit(SocketEvent.ROOM_JOIN.getName(), obj);
	}

	
	@Test	
	public void connectTest() {
		socket.on("connect", new TestableEmitterListener() {
			
			@Override
			public void call(Object... args) {
				// TODO Auto-generated method stub
				assertThat(socket.connected()).isEqualTo(false );				
			}
		});
	}
	
	@Test
	void send() {		
		JSONObject obj = new JSONObject();
		obj.put("writer", "star8076");
		obj.put("roomId", "5f46029fead5c71c954ddd96");
		obj.put("msg", "test message");		
		socket.on(SocketEvent.MESSAGE_RECEIVE.getName(), new TestableEmitterListener() {
			
			@Override
			public void call(Object... args) {
				// TODO Auto-generated method stub
				JSONObject res = (JSONObject) args[0];
				System.out.println(res);
			}
		});
		socket.emit(SocketEvent.MESSAGE_SEND.getName(), obj);

	}

	public void waitConnect(Socket socket, long waitTerm, int tryCount) throws InterruptedException {
		int check = 0;
		while (true) {
			if (socket.connected()) {
				break;
			}
			check++;
			if (check >= tryCount) {
				break;
			}
			Thread.sleep(waitTerm);
		}
	}

}
