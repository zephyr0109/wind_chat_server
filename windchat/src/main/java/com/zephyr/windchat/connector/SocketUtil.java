package com.zephyr.windchat.connector;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.protocol.Packet;
import com.corundumstudio.socketio.protocol.PacketType;

/**
 * Socket 데이터 관련 클래스 헤더정보 등 설정에 사용할 목적으로 생성함
 * 
 * @author zephyr
 *
 */
@Component
public class SocketUtil {
    /**
     * JSON 형태의 데이터를 packet 형태로 변환. 현재는 사용하지 않음
     * 
     * @param event 이벤트 명
     * @param obj   전송할 데이터
     * @return 생성한 패킷 객체
     */
	public Packet makeJsonPacket(String event, JSONObject obj) {
		Packet packet = new Packet(PacketType.EVENT);
		packet.setName(event);
		packet.setData(obj);
		return packet;
	}
}
