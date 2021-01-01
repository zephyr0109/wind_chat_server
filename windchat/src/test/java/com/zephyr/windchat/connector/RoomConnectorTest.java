package com.zephyr.windchat.connector;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.net.URISyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.zephyr.windchat.etc.SocketEvent;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class RoomConnectorTest {

	private Socket socket;

	@BeforeEach
	public void init() throws URISyntaxException, InterruptedException {
		socket = IO.socket("http://localhost:8081");
		socket.connect();
		JSONObject info = new JSONObject();
		info.put("userId", "zephyr0109");
		info.put("password", "test");
		socket.emit(SocketEvent.LOGIN.getName(), info);
		waitConnect(socket, 1000L, 10);
	}

	@Test
	public void connectTest() {
		socket.on("connect", new TestableEmitterListener() {

			@Override
			public void call(Object... args) {
				// TODO Auto-generated method stub
				assertThat(socket.connected()).isEqualTo(false);
			}
		});
	}

	@Test
	public void createRoom() throws JSONException {
		JSONArray users = new JSONArray();
		String owner = "zephyr0109";
		String name = "test room";

		users.put(owner);
		users.put("ccw");

		JSONObject room = new JSONObject();
		room.put("name", name);
		room.put("owner", owner);
		room.put("users", users);
		socket.on(SocketEvent.ROOM_CREATE.getName(), new TestableEmitterListener() {

			@Override
			public void call(Object... args) {
				JSONObject json = (JSONObject) args[0];
				assertNotNull(json.get("roomId"));
				assertThat(json.get("name")).isEqualTo(name);
				assertThat(json.get("owner")).isEqualTo(owner);
				System.out.println("create test done");

			}
		});
		socket.emit(SocketEvent.ROOM_CREATE.getName(), room);

	}

	@Test	
	@Disabled
	public void deleteTest() {
		JSONArray users = new JSONArray();
		users.put("zephyr0109");
		users.put("ccw");

		JSONObject room = new JSONObject();
		room.put("roomId", "bf35e520-f8a8-48cc-82ad-88f22ceac382");
		socket.on(SocketEvent.ROOM_DELETE.getName(), new TestableEmitterListener() {

			@Override
			public void call(Object... args) {
				// TODO Auto-generated method stub
				try {

					JSONObject json = getJSONObject(args[0]);
					assertThat(json.get("result")).isEqualTo(true);
					System.out.println("del test done");
				} catch (JSONException e) {
					e.printStackTrace();
					fail();
				}

			}

		});
		socket.emit(SocketEvent.ROOM_DELETE.getName(), room);
	}

	@Test
	public void findByUserIdTest() {
		JSONObject info = new JSONObject();
		info.put("userId", "zephyr0109");
		info.put("password", "test");
		socket.once(SocketEvent.LOGIN.getName(), new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				JSONObject param = new JSONObject();
				param.put("userId", "zephyr0109");
				socket.on(SocketEvent.ROOM_LIST.getName(), new TestableEmitterListener() {
					@Override
					public void call(Object... args) {
						// TODO Auto-generated method stub
						try {
							JSONObject obj = (JSONObject) args[0];
							JSONArray array = obj.getJSONArray("roomList");							
							assertThat(array.length()).isNotEqualTo(0);							

						} catch (Exception e) {
							e.printStackTrace();
							fail();
						}

					}

				});

				socket.emit(SocketEvent.ROOM_LIST.getName(), param);

			}
		});
		socket.emit(SocketEvent.LOGIN.getName(), info);

	}

	@Test
	public void renameTest() {

		JSONObject obj = new JSONObject();
		String updateName = "updated2";
		obj.put("roomId", "bf35e520-f8a8-48cc-82ad-88f22ceac382");
		obj.put("rename", updateName);
		socket.emit(SocketEvent.ROOM_RENAME.getName(), obj);
			
	}

	@Test
	public void roomJoin() {
		JSONObject obj = new JSONObject();
		obj.put("userId", "star8076");
		obj.put("roomId", "test1");

		socket.on(SocketEvent.ROOM_JOIN.getName(), new TestableEmitterListener() {

			@Override
			public void call(Object... args) {
				JSONObject result = (JSONObject) args[0];
				assertThat(result.get("res")).isEqualTo(true);

				log.info("room info : {}", result.get("room"));

			}
		});

		socket.emit(SocketEvent.ROOM_JOIN.getName(), obj);
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
