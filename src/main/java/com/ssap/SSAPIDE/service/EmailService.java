package com.ssap.SSAPIDE.service;

import com.ssap.SSAPIDE.domain.member.MemberRepository;
import com.ssap.SSAPIDE.domain.member.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService {

    private final MemberRepository memberRepository;

    public Boolean checkEmail(String email) {
        Boolean isAvailable;

        Optional<User> optionalUser = memberRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            isAvailable = false;
        } else {
            isAvailable = true;
        }

        return isAvailable;
    }
}
