package com.ssap.SSAPIDE.controller;

import com.ssap.SSAPIDE.dto.EmailAvailability;
import com.ssap.SSAPIDE.dto.ResponseDto;
import com.ssap.SSAPIDE.dto.SignupRequest;
import com.ssap.SSAPIDE.dto.SignupResponse;
import com.ssap.SSAPIDE.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequiredArgsConstructor
@RestController
public class SignupController {

    private final EmailService emailService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity illegalExHandler(IllegalArgumentException e) {
        log.error("", e);
        ResponseDto<String> responseDto = new ResponseDto<String>("", "");
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ResponseDto<String> exHandler(Exception e) {
        log.error("", e);
        return new ResponseDto<String>("요청을 처리하는 중에 서버에서 오류가 발생했습니다.", "");
    }

    @GetMapping("/users/check-email/{email}")
    public ResponseEntity<ResponseDto> checkEmailDuplication(
            @PathVariable String email
    ) {
        Boolean isAvailability = emailService.checkEmail(email);

        EmailAvailability emailAvailability = new EmailAvailability();
        emailAvailability.setIsAvailable(isAvailability);

        String message = isAvailability ? "이메일 사용 가능합니다" : "이메일이 이미 사용 중입니다.";

        return ResponseEntity.ok(ResponseDto.success(message, emailAvailability));
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signup(
            @RequestBody SignupRequest request
    ) {
        //TODO: 입력값 검증 로직 필요(400번 오류 관련).

        if (!request.getPassword().equals(request.getPasswordConfirm())) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResponseDto.error("비밀번호가 일치하지 않습니다.", ""));
        }

        try {
            //TODO:
            // DB 저장할 때 복호화 못하게 해야 함.
            // MySQL DB 연동테스트 필요.

            SignupResponse response = new SignupResponse();
            response.setEmail(request.getEmail());
            response.setName(request.getName());

            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.success("회원가입 성공", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDto.error("요청을 처리하는 중에 서버에서 오류가 발생했습니다.", ""));
        }
    }
}
