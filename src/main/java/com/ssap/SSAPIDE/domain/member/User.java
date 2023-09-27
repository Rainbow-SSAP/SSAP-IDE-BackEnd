package com.ssap.SSAPIDE.domain.member;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id; // memory repository 이용 필드

    private String email;
    private String password;
    private String name;
}
