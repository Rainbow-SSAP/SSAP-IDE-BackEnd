package com.ssap.SSAPIDE.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseDto<T> {
    private String message;
    private T data;

    public static <T> ResponseDto<T> success(String message, T data) {
        return new ResponseDto<>(message, data);
    }

    public static <T> ResponseDto<T> error(String message, T data) {
        return new ResponseDto<>(message, data);
    }
}
