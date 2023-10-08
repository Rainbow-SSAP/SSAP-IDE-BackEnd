package com.ssap.SSAPIDE.controller;

import com.ssap.SSAPIDE.domain.member.User;
import com.ssap.SSAPIDE.dto.*;
import com.ssap.SSAPIDE.service.EmailService;
import com.ssap.SSAPIDE.service.SignupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class SignupController {

    private final EmailService emailService;
    private final SignupService signupService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity illegalExHandler(IllegalArgumentException e) {
        log.error("IllegalArgumentException", e);
        ResponseDto<String> responseDto = new ResponseDto<String>("입력 값 검증 오류", "");
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
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
            @Validated @RequestBody SignupRequest request, BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors()) {
            List<ErrorField> errors = bindingResult.getFieldErrors().stream()
                    .map(error -> new ErrorField(error.getField()))
                    .collect(Collectors.toList());

            return ResponseEntity.badRequest().body(ResponseDto.error("입력 값 검증 오류", errors));
        }

        if (!request.getPassword().equals(request.getPasswordConfirm())) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ResponseDto.error("비밀번호가 일치하지 않습니다.", ""));
        }

        try {
            User newUser = signupService.signup(request);
            Optional<User> optionalNewUser = Optional.ofNullable(newUser);
            if (!optionalNewUser.isPresent()) {
                EmailAvailability emailAvailability = new EmailAvailability();
                emailAvailability.setIsAvailable(false);
                return ResponseEntity.status(HttpStatus.OK).body(ResponseDto.error("이메일이 이미 사용 중입니다.", emailAvailability));
            }

            SignupResponse response = new SignupResponse();
            response.setEmail(newUser.getEmail());
            response.setName(newUser.getName());

            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDto.success("회원가입 성공", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseDto.error("요청을 처리하는 중에 서버에서 오류가 발생했습니다.", ""));
        }
    }
}
