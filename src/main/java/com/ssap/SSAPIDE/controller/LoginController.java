package com.ssap.SSAPIDE.controller;

import com.ssap.SSAPIDE.domain.member.User;
import com.ssap.SSAPIDE.dto.LoginRequest;
import com.ssap.SSAPIDE.dto.ResponseDto;
import com.ssap.SSAPIDE.service.LoginService;
import com.ssap.SSAPIDE.session.SessionLoginConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public ResponseEntity<ResponseDto> login(
            @Validated @RequestBody LoginRequest loginRequest,
            HttpServletRequest request
            ) {
        User loginUser = loginService.login(loginRequest);
        if (loginUser != null) {
            HttpSession session = request.getSession(true);
            session.setAttribute(SessionLoginConst.LOGIN_USER, loginUser);
            return ResponseEntity.ok(ResponseDto.success("로그인 성공.", "")); // data 부분은 현재 Token이 완성되면 추가 예정.
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseDto.error("이메일 또는 비밀번호를 다시 확인해주세요.", ""));
        }
    }
}
