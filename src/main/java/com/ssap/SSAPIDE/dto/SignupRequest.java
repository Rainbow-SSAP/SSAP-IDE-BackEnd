package com.ssap.SSAPIDE.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SignupRequest {
    private String email;
    private String password;
    private String passwordConfirm;
    private String name;
}
