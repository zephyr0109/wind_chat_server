package com.zephyr.windchat.etc;

/**
 * 소켓 이벤트를 enum으로 선언.
 * @author zephyr
 *
 */
public enum SocketEvent {

    USER_REGISTER("user.register"),
    LOGIN("login"),
    
    ROOM_CREATE("room.create"),
    ROOM_DELETE("room.delete"),
    ROOM_UPDATE("room.update"),
    ROOM_INVITE("room.invite"),
    ROOM_RENAME("room.rename"),
    ROOM_FIND("room.find"),
    ROOM_JOIN("room.join"),
    ROOM_LIST("room.list"),
    ROOM_DETAIL("room.detail"),
    MESSAGE_RECEIVE("message.receive"),
    MESSAGE_SEND("message.send");

    final private String name;

    public String getName() {
        return name;
    }

    private SocketEvent(String name) {
        this.name = name;

    }
}
