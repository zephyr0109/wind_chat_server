package com.zephyr.windchat.connector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.zephyr.windchat.domain.Room;
import com.zephyr.windchat.domain.User;
import com.zephyr.windchat.etc.SocketEvent;
import com.zephyr.windchat.service.RoomService;

/**
 * 대화방 관련 소켓 서버
 * 
 * @author zephyr
 *
 */
@Component
public class RoomConnector {

    private SocketIOServer socketServer;
    private RoomService roomService;

    @Autowired
    public RoomConnector(SocketIOServer socketServer, RoomService roomService) {
        // TODO : 수신된 데이터에 인증 처리 여부 확인 필요. 프로젝트 분리를 염두에 둬야 함.

        this.socketServer = socketServer;
        this.roomService = roomService;
        this.socketServer.addListeners(this);

    }

    /**
     * 방 생성
     * 
     * @param client
     * @param room      대화방 정보
     * @param ackSender
     */
    @OnEvent(value = "room.create")
    public void createRoom(SocketIOClient client, Room room, AckRequest ackSender) {
        String roomId = UUID.randomUUID().toString();
        room.setRoomId(roomId);
        Room result = roomService.createRoom(room);
        client.sendEvent(SocketEvent.ROOM_CREATE.getName(), result);
    }

    /**
     * 대화방 이름 변경
     * 
     * @param client
     * @param obj       JSON type으로 rename, roomId가 포함되어 있음
     * @param ackSender
     */
    @OnEvent(value = "room.rename")
    public void rename(SocketIOClient client, Map<String, Object> obj, AckRequest ackSender) {
        // TODO : login check, update
        String rename = (String) obj.get("rename");
        String id = (String) obj.get("roomId");
        Room room = roomService.getRoomDetail(id);
        room.setName(rename);
        Room result = roomService.createRoom(room);

        client.sendEvent(SocketEvent.ROOM_RENAME.getName(), result);
    }

    /**
     * 대화방 삭제
     * 
     * @param client
     * @param data      대화방 정보 수신.
     * @param ackSender
     */
    @OnEvent(value = "room.delete")
    public void delete(SocketIOClient client, Room data, AckRequest ackSender) {
        boolean result = roomService.deleteRoom(data.getRoomId());
        Map<String, Object> resobj = new HashMap<>();
        resobj.put("result", result);
        client.sendEvent(SocketEvent.ROOM_DELETE.getName(), resobj);
    }

    /**
     * 대화방 초대
     * 
     * @param client
     * @param data      JSON 형태, userId에 사용자 id가 list형태로 넘어옴
     * @param ackSender
     */
    @OnEvent(value = "room.invite")
    public void invite(SocketIOClient client, Map<String, Object> data, AckRequest ackSender) {
        String roomId = (String) data.get("roomId");

        // stream 형태로 변경 가능할 것으로 보임
        List<String> userIds = (List<String>) data.get("userId");
        Room updatedRoom = roomService.addUserInRoom(roomId, userIds);

        client.sendEvent(SocketEvent.ROOM_INVITE.getName(), updatedRoom);
    }

    /**
     * 방 상세 정보 조회
     * 
     * @param client
     * @param data      roomId 포함
     * @param ackSender
     */
    @OnEvent(value = "room.detail")
    public void detail(SocketIOClient client, Map<String, Object> data, AckRequest ackSender) {

        String roomId = (String) data.get("roomId");
        Room room = roomService.getRoomDetail(roomId);
        client.sendEvent(SocketEvent.ROOM_DETAIL.getName(), room);
    }

    /**
     * 접속 사용자에 대한 대화방 리스트
     * 
     * @param client
     * @param param
     * @param ackRequest
     */
    @OnEvent("room.list")
    public void roomList(SocketIOClient client, Map<String, Object> param, AckRequest ackRequest) {

        // TODO 프로젝트 분리를 염두에 둘 경우 아래의 사용자 아이디 조회 대신 별도 방법 필요
        User userInfo = client.get("userInfo");
        String userid = userInfo.getUserid();

        // 대화방 조회
        List<Room> roomlist = roomService.findRoomByUserId(userid);

        // 결과를 JSON 형태로 전송. 서버에서는 Map으로 전달하면 됨
        Map<String, Object> result = new HashMap<>();
        result.put("roomList", roomlist);
        client.sendEvent(SocketEvent.ROOM_LIST.getName(), result);
    }

    /**
     * 대화방 입장
     * 
     * @param client
     * @param param
     * @param ackRequest
     */
    @OnEvent(value = "room.join")
    public void roomJoin(SocketIOClient client, Map<String, Object> param, AckRequest ackRequest) {

        String roomId = (String) param.get("roomId");
        // 소켓 서버에서 해당 대화방 id를 통해 대화방으로 join함
        client.joinRoom(roomId);

        // 대화방 상세 정보를 전달
        Room room = roomService.getRoomDetail(roomId);
        Map<String, Object> result = new HashMap<>();
        result.put("res", true);
        result.put("room", room);
        // 소켓 서버에서 대화방을 가져온 후 해당 대화방에 join 결과를 보냄. 후에 입장했다는 메시지로 변경할 것
        socketServer.getRoomOperations(roomId).sendEvent(SocketEvent.ROOM_JOIN.getName(), result);

        // join 결과와 상세 정보를 전달
        client.sendEvent(SocketEvent.ROOM_JOIN.getName(), result);

    }

}
