package com.ssap.SSAPIDE.service;

import com.ssap.SSAPIDE.dto.ContainerResponseDto;
import com.ssap.SSAPIDE.dto.ContainerUpdateRequestDto;
import com.ssap.SSAPIDE.model.Container;
import com.ssap.SSAPIDE.repository.ContainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContainerService {
    private final ContainerRepository containerRepository;
    private final DockerService dockerService;

    public ContainerResponseDto createContainer(String title, String description, String stack, String customControl) throws InterruptedException {
        try {
//            dockerService.pullImage(stack);
            String containerId = dockerService.createContainer(title, stack);

            // 현재 시간을 생성 시간으로 설정합니다.
            LocalDateTime createdAt = LocalDateTime.now();

            // 컨테이너 객체를 생성하고 상세 정보를 설정합니다.
            Container container = new Container();
            container.setContainerId(containerId);
            container.setTitle(title);
            container.setDescription(description);
            container.setStack(stack);
            container.setCustomControl(customControl);
            container.setCreatedAt(createdAt);

            // 컨테이너 객체를 데이터베이스에 저장합니다.
            containerRepository.save(container);

            // 응답을 생성하고 containerId와 createdAt을 설정합니다.
            ContainerResponseDto response = new ContainerResponseDto();
            response.setContainerId(containerId);
            response.setCreatedAt(createdAt);

            // 응답을 반환합니다.
            return response;
        } catch (Exception e) {
            throw new RuntimeException("컨테이너 생성 중 에러가 발생했습니다.", e);
        }
    }


    public void updateContainer(ContainerUpdateRequestDto dto) {
        Optional<Container> optionalContainer = containerRepository.findById(dto.getContainerId());
        if (optionalContainer.isPresent()) {
            Container existingContainer = optionalContainer.get();
            if (dto.getDescription() != null) { // 수정하려는 description 값이 전송되었는지 확인
                existingContainer.setDescription(dto.getDescription());
            }
            containerRepository.save(existingContainer);
        } else {
            throw new NoSuchElementException("지정된 ID에 해당하는 컨테이너가 존재하지 않습니다.");
        }
    }

    public void deleteContainer(String containerId) {
         dockerService.deleteContainer(containerId);
        // DB에서 해당 컨테이너 삭제
         containerRepository.deleteById(containerId);
    }

    public void runContainer(String containerId) {
        dockerService.runContainer(containerId);
    }

    public Container getContainerById(String containerId) {
        return containerRepository.findById(containerId)
                .orElseThrow(() -> new IllegalArgumentException("Container not found for id: " + containerId));
    }
}
