package com.ssap.SSAPIDE.controller;

import com.ssap.SSAPIDE.domain.member.User;
import com.ssap.SSAPIDE.dto.LoginRequest;
import com.ssap.SSAPIDE.dto.ResponseDto;
import com.ssap.SSAPIDE.service.LoginService;
import com.ssap.SSAPIDE.session.SessionLoginConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity illegalExHandler(IllegalArgumentException e) {
        log.error("IllegalArgumentException", e);
        ResponseDto<String> responseDto = new ResponseDto<String>("입력 값 검증 오류", "");
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(
            @Validated @RequestBody LoginRequest loginRequest,
            HttpServletRequest request, HttpServletResponse response
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

    @PostMapping("/logout")
    public ResponseEntity<ResponseDto> logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok(ResponseDto.success("로그아웃 성공.", ""));
    }

    @GetMapping("/")
    public ResponseEntity<ResponseDto> logout(@SessionAttribute(name = SessionLoginConst.LOGIN_USER, required = false) User loginUser) {
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseDto.error("로그아웃 홈 이동", ""));
        }
        return ResponseEntity.ok(ResponseDto.success("로그인 홈 이동", ""));
    }
}
