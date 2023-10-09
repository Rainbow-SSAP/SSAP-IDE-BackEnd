package com.ssap.SSAPIDE.controller;

import com.ssap.SSAPIDE.domain.member.User;
import com.ssap.SSAPIDE.dto.ContainerDetailsResponseDto;
import com.ssap.SSAPIDE.dto.ContainerRequestDto;
import com.ssap.SSAPIDE.dto.ContainerResponseDto;
import com.ssap.SSAPIDE.dto.ContainerUpdateRequestDto;
import com.ssap.SSAPIDE.service.ContainerService;
import com.ssap.SSAPIDE.session.SessionLoginConst;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/containers")
@CrossOrigin(origins = "*")
public class ContainerController {
    @Autowired
    private ContainerService containerService;

    @PostMapping("/create")
    public ResponseEntity<?> createContainer(
            @Valid @RequestBody ContainerRequestDto requestDto,
            @SessionAttribute(name = SessionLoginConst.LOGIN_USER, required = false) User loginUser
    ) {
        try {
            ContainerResponseDto container = containerService.createContainer(
                    requestDto.getTitle(),
                    requestDto.getDescription(),
                    requestDto.getStack(),
                    requestDto.getCustomControl(),
                    loginUser);
            log.info("Container created with ID: {}", container.getContainerId());
            return ResponseEntity.status(HttpStatus.CREATED).body(
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
    @PatchMapping("/{containerId}")
    public ResponseEntity<?> updateContainer(@PathVariable String containerId,
                                             @RequestBody ContainerUpdateRequestDto requestDto) {
        try {
            requestDto.setContainerId(containerId);
            containerService.updateContainer(requestDto);

            return ResponseEntity.ok().body("");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body("특수문자는 이름에 입력할 수 없습니다.");
        } catch (SecurityException e) {
            return ResponseEntity.status(403).body("컨테이너를 수정할 권한이 없습니다.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body("지정된 ID에 해당하는 컨테이너가 존재하지 않습니다.");
        } catch (Exception e) {
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

    @GetMapping("/{containerId}")
    public ResponseEntity<?> getContainerDetails(@PathVariable String containerId) {
        try {
            ContainerDetailsResponseDto container = containerService.getContainerDetails(containerId);
            return ResponseEntity.ok(container);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body("지정된 ID에 해당하는 컨테이너가 존재하지 않습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body( "요청을 처리하는 중에 서버에서 오류가 발생했습니다.");
        }
    }
}

