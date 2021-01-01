package com.zephyr.windchat.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zephyr.windchat.domain.Message;
import com.zephyr.windchat.domain.MessageRepository;
import com.zephyr.windchat.domain.Room;
import com.zephyr.windchat.domain.RoomRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 대화방 관련 서비스
 * 
 * @author zephyr
 *
 */
@Service
public class RoomService {

    private RoomRepository roomRepository;
    private MessageRepository messageRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository, MessageRepository messageRepository) {
        this.roomRepository = roomRepository;
        this.messageRepository = messageRepository;
    }

    // 대화방 저장
    public Room createRoom(Room room) {
        Mono<Room> roomResult = roomRepository.save(room);
        if (roomResult != null) {
            return roomResult.block();
        } else {
            return null;
        }
    }

    // 대화방 수정. 저장과 같은 방식이나 유효성 등 추가할 일이 있을 듯 하여 일단 분리만 해둠
    public Room modifyRoom(Room room) {
        Mono<Room> roomResult = roomRepository.save(room);
        if (roomResult != null) {
            return roomResult.block();
        } else {
            return null;
        }
    }

    // 대화방 삭제
    // 해당 기능은 아예 삭제하는 기능으로 대화방 나가기는 별도의 기능으로 구현 예정
    public boolean deleteRoom(String roomId) {
        try {
            Mono<Void> delResult = roomRepository.deleteById(roomId);
            delResult.block();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 대화방 나가기
     * 
     * @param roomId 나가려는 대화방 id
     * @param userId 나갈 사용자 id
     * @return
     */
    public boolean leaveRoom(String roomId, String userId) {
        // TODO 절차 : 대화방이 존재하는지 확인 -> 해당 대화방에 사용자가 포함되어있는지 확인 -> 대화방의 생성자인지 확인 -> owner가 아니고
        // 포함되어있을 경우 삭제
        return false;
    }

    // 로그인 이후 대화목록을 가져올 때 사용할 기능
    public List<Room> findRoomByUserId(String userId) {
        List<String> userIdList = new ArrayList<>();
        userIdList.add(userId);
        Flux<Room> findFlux = roomRepository.findByUsersInUser(userIdList);
        // Flux<Room> findFlux = roomRepository.findAll();
        if (findFlux != null) {
            return findFlux.collectList().block();
        } else {
            return null;
        }

    }

    // 대화방 초대 시 사용할 기능으로 사용자 추가
    public Room addUserInRoom(String roomId, List<String> userId) {
        Room room = roomRepository.findById(roomId).block();

        return addUserInRoom(room, userId);
    }

    // 대화방 초대 시 사용할 기능으로 사용자 추가
    public Room addUserInRoom(Room room, List<String> userId) {
        room.getUsers().addAll(userId);
        Room roomResult = roomRepository.save(room).block();
        return roomResult;
    }

    public Room getRoomDetail(String roomId) {
        Room room = roomRepository.findById(roomId).block();
        if (room == null) {
            return null;
        }
        Flux<Message> msgFlux = messageRepository.findByRoomId(room.getRoomId());
        room.setMsgs(msgFlux.buffer().blockFirst());
        return room;
    }

}
