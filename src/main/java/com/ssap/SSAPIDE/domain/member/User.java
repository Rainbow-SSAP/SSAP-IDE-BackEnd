package com.ssap.SSAPIDE.domain.member;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id; // memory repository 이용 필드

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String name;
}
