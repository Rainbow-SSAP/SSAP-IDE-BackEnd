package com.ssap.SSAPIDE.service;

import com.ssap.SSAPIDE.domain.member.MemberRepository;
import com.ssap.SSAPIDE.domain.member.User;
import com.ssap.SSAPIDE.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService {

    private final UserRepository userRepository;

    public Boolean checkEmail(String email) {
        Boolean isAvailable = userRepository.existsByEmail(email);
        return isAvailable;
    }
}
