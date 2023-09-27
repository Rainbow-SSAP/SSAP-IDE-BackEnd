package com.ssap.SSAPIDE.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Repository
public class MemberRepository {
    private static Map<Long, User> store = new ConcurrentHashMap<>();
    private static long sequence = 0L;

    public User save(User user) {
        user.setId(++sequence);
        log.info("save: member = {}", user);
        store.put(user.getId(), user);
        return user;
    }

    public User findById(Long id) {
        return store.get(id);
    }

    public Optional<User> findByEmail(String email) {

        return findAll().stream()
                .filter(m -> m.getEmail().equals(email))
                .findFirst();
    }

    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
