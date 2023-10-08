package com.ssap.SSAPIDE.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CheckUserResponse {

    private Long memberId;
    private String email;
    private String name;
}
