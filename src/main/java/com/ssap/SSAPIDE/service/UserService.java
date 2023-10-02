package com.ssap.SSAPIDE.service;

import com.ssap.SSAPIDE.domain.member.User;
import com.ssap.SSAPIDE.dto.SignupRequest;
import com.ssap.SSAPIDE.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User signup(SignupRequest signupRequest) {
        if(userRepository.existsByEmail(signupRequest.getEmail())) {
            return new User();
        }

        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        return userRepository.save(user);
    }
}
