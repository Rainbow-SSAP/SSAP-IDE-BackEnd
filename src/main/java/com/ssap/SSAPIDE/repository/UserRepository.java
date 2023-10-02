package com.ssap.SSAPIDE.repository;

import com.ssap.SSAPIDE.domain.member.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
