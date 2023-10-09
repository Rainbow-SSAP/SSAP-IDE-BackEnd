package com.ssap.SSAPIDE.service;

import com.ssap.SSAPIDE.domain.member.User;
import com.ssap.SSAPIDE.dto.UserContainerResponse;
import com.ssap.SSAPIDE.repository.ContainerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final ContainerRepository containerRepository;

    public List<UserContainerResponse> getUserContainers(User user) {
        return containerRepository.findByUser_MemberId(user.getMemberId()).stream()
                .map(container -> new UserContainerResponse(
                        container.getContainerId(),
                        container.getTitle(),
                        container.getDescription()
                )).collect(Collectors.toList());
    }
}
