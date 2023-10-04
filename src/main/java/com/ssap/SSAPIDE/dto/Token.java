package com.ssap.SSAPIDE.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Token {
    private final String accessToken;
    private final String refreshToken;
}
