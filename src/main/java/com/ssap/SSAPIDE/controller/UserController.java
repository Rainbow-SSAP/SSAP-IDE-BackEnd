package com.ssap.SSAPIDE.controller;

import com.ssap.SSAPIDE.domain.member.User;
import com.ssap.SSAPIDE.dto.CheckUserResponse;
import com.ssap.SSAPIDE.dto.ResponseDto;
import com.ssap.SSAPIDE.session.SessionLoginConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;


@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @GetMapping("/users")
    public ResponseEntity<ResponseDto> checkUser(@SessionAttribute(name = SessionLoginConst.LOGIN_USER, required = false) User loginUser) {
        CheckUserResponse checkUserResponse = new CheckUserResponse();
        checkUserResponse.setMemberId(loginUser.getMemberId());
        checkUserResponse.setEmail(loginUser.getEmail());
        checkUserResponse.setName(loginUser.getName());
        return ResponseEntity.ok(ResponseDto.success("회원정보 조회 성공", checkUserResponse));
    }
}
