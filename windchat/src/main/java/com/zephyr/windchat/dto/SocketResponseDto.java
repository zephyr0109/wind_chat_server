package com.zephyr.windchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocketResponseDto {
    private String result;
    private Object data;
}
