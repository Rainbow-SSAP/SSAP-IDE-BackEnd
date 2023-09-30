package com.ssap.SSAPIDE.exception;

import com.ssap.SSAPIDE.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.ssap.SSAPIDE.controller")
public class ExceptionController {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ResponseDto<String> exHandler(Exception e) {
        log.error("", e);
        return new ResponseDto<String>("요청을 처리하는 중에 서버에서 오류가 발생했습니다.", "");
    }
}
