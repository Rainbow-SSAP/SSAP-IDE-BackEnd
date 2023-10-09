package com.ssap.SSAPIDE.controller;

import com.ssap.SSAPIDE.domain.member.User;
import com.ssap.SSAPIDE.dto.CheckUserResponse;
import com.ssap.SSAPIDE.dto.ResponseDto;
import com.ssap.SSAPIDE.dto.UserContainerResponse;
import com.ssap.SSAPIDE.service.UserService;
import com.ssap.SSAPIDE.session.SessionLoginConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity illegalExHandler(IllegalArgumentException e) {
        log.error("IllegalArgumentException", e);
        ResponseDto<String> responseDto = new ResponseDto<String>("입력 값 검증 오류", "");
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/users")
    public ResponseEntity<ResponseDto> checkUser(@SessionAttribute(name = SessionLoginConst.LOGIN_USER, required = false) User loginUser) {
        CheckUserResponse checkUserResponse = new CheckUserResponse();
        checkUserResponse.setMemberId(loginUser.getMemberId());
        checkUserResponse.setEmail(loginUser.getEmail());
        checkUserResponse.setName(loginUser.getName());
        return ResponseEntity.ok(ResponseDto.success("회원정보 조회 성공", checkUserResponse));
    }

    @GetMapping("/user/containers")
    public ResponseEntity<ResponseDto> getUserContainers(@SessionAttribute(name = SessionLoginConst.LOGIN_USER, required = false) User loginUser) {
        List<UserContainerResponse> userContainers = userService.getUserContainers(loginUser);
        return ResponseEntity.ok(ResponseDto.success("회원 컨테이너 조회 성공", userContainers));
    }
}
