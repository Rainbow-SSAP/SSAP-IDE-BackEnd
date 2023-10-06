package com.ssap.SSAPIDE.controller;

import com.ssap.SSAPIDE.dto.ContainerRequestDto;
import com.ssap.SSAPIDE.dto.ContainerResponseDto;
import com.ssap.SSAPIDE.service.ContainerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/containers")
    public class ContainerController {
    @Autowired
    private ContainerService containerService;

    @PostMapping("/create")
    public ResponseEntity<?> createContainer(@Valid @RequestBody ContainerRequestDto requestDto) {
        try {
            ContainerResponseDto container = containerService.createContainer(
                    requestDto.getTitle(),
                    requestDto.getDescription(),
                    requestDto.getStack(),
                    requestDto.getCustomControl());
            log.info("Container created with ID: {}", container.getContainerId());
            return ResponseEntity.ok().body(
                    new ContainerResponseDto(container.getContainerId(), container.getCreatedAt()));
        } catch (Exception e) {
            log.error("Error while creating container", e);
            return ResponseEntity.status(500).body("요청을 처리하는 중에 서버에서 오류가 발생했습니다.");
        }
    }

    @DeleteMapping("/{containerId}")
    public ResponseEntity<?> deleteContainer(@PathVariable String containerId) {
        try {
            containerService.deleteContainer(containerId);
            return ResponseEntity.ok().body(
                    new ContainerResponseDto(containerId, null));
        } catch (Exception e) {
            // 로그 작성 또는 다른 오류 처리
            return ResponseEntity.status(500).body("요청을 처리하는 중에 서버에서 오류가 발생했습니다.");
        }
    }

    @PostMapping("/run/{containerId}")
    public ResponseEntity<?> runContainer(@PathVariable String containerId) {
        try {
            containerService.runContainer(containerId);
            return ResponseEntity.ok().body(containerId);
        } catch (Exception e) {
            log.error("Error while starting container", e);
            return ResponseEntity.status(500).body("요청을 처리하는 중에 서버에서 오류가 발생했습니다.");
        }
    }


}

