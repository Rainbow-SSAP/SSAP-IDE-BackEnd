package com.ssap.SSAPIDE.controller;

import com.ssap.SSAPIDE.dto.FileAndFolderCreateRequestDto;
import com.ssap.SSAPIDE.dto.FolderRenameRequestDto;
import com.ssap.SSAPIDE.service.FileAndFolderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/ide/{containerId}/folders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FolderController {

    private final FileAndFolderService fileAndFolderService;

    @PostMapping
    public ResponseEntity<?> createFolder(@PathVariable String containerId,
                                                @Valid @RequestBody FileAndFolderCreateRequestDto requestDto) {
        try {
            requestDto.setContainerId(containerId);
            String createdName = fileAndFolderService.createFileOrFolder(requestDto);

            return ResponseEntity.status(201).body(
                    Map.of("status", 201,
                            "message", "폴더 생성 성공"));
        } catch (IllegalArgumentException e) {
            log.error("Error due to illegal argument: {}", e.getMessage());
            return ResponseEntity.status(400).body(Map.of("status", 400, "message", "파라미터 필수 항목이 누락되었거나 형식이 잘못되었습니다."));
        } catch (SecurityException e) {
            log.error("Error due to security constraints: {}", e.getMessage());
            return ResponseEntity.status(403).body(Map.of("status", 403, "message", "해당 경로에 폴더를 생성할 권한이 없습니다."));
        } catch (NoSuchElementException e) {
            log.error("Error due to non-existent element: {}", e.getMessage());
            return ResponseEntity.status(404).body(Map.of("status", 404, "message", "지정된 containerId에 해당하는 컨테이너가 존재하지 않습니다."));
        } catch (Exception e) {
            log.error("Error while creating file or folder: {}", e.getMessage());
            if (e.getMessage().contains("Conflict")) {
                return ResponseEntity.status(409).body(Map.of("status", 409, "message", "동일한 이름의 폴더가 이미 해당 경로에 존재합니다."));
            } else {
                return ResponseEntity.status(500).body(Map.of("status", 500, "message", "요청을 처리하는 중에 서버에서 오류가 발생했습니다."));
            }
        }
    }

    // 추가적인 폴더 관련 API 메서드들 (예: 수정, 삭제 등)을 여기에 구현
    @DeleteMapping("/{folderId}")
    public ResponseEntity<?> deleteFolder(@PathVariable String containerId, @PathVariable Long folderId) {
        try {
            fileAndFolderService.deleteFolder(containerId, folderId);

            return ResponseEntity.ok().body(Map.of("status", 200, "message", "폴더 삭제 완료"));
        } catch (IllegalArgumentException e) {
            log.error("Error due to illegal argument: {}", e.getMessage());
            return ResponseEntity.status(400).body(Map.of("status", 400, "message", e.getMessage()));
        } catch (SecurityException e) {
            log.error("Error due to security constraints: {}", e.getMessage());
            return ResponseEntity.status(403).body(Map.of("status", 403, "message", "해당 폴더를 삭제할 권한이 없습니다."));
        } catch (NoSuchElementException e) {
            log.error("Error due to non-existent element: {}", e.getMessage());
            return ResponseEntity.status(404).body(Map.of("status", 404, "message", "지정된 경로에 해당하는 폴더가 존재하지 않습니다."));
        } catch (Exception e) {
            log.error("Error while deleting folder: {}", e.getMessage());
            return ResponseEntity.status(500).body(Map.of("status", 500, "message", "요청을 처리하는 중에 서버에서 오류가 발생했습니다."));
        }
    }

    @PatchMapping("/{folderId}/rename")
    public ResponseEntity<?> renameFolder(@PathVariable String containerId,
                                          @PathVariable Long folderId,
                                          @RequestBody FolderRenameRequestDto requestDto) {
        try {
            if (requestDto.getNewFolderName() == null || requestDto.getNewFolderName().isBlank()) {
                throw new IllegalArgumentException("파라미터 필수 항목이 누락되었거나 형식이 잘못되었습니다.");
            }
            fileAndFolderService.renameFolder(containerId, folderId, requestDto.getNewFolderName());

            return ResponseEntity.ok().body(Map.of("status", 200, "message", "폴더명 수정 완료"));
        } catch (IllegalArgumentException e) {
            log.error("Error due to illegal argument: {}", e.getMessage());
            return ResponseEntity.status(400).body(Map.of("status", 400, "message", e.getMessage()));
        } catch (SecurityException e) {
            log.error("Error due to security constraints: {}", e.getMessage());
            return ResponseEntity.status(409).body(Map.of("status", 409, "message", "동일한 이름의 폴더가 이미 해당 경로에 존재합니다."));
        } catch (NoSuchElementException e) {
            log.error("Error due to non-existent element: {}", e.getMessage());
            return ResponseEntity.status(404).body(Map.of("status", 404, "message", "지정된 containerId에 해당하는 컨테이너가 존재하지 않습니다."));
        } catch (Exception e) {
            log.error("Error while renaming folder: {}", e.getMessage());
            return ResponseEntity.status(500).body(Map.of("status", 500, "message", "요청을 처리하는 중에 서버에서 오류가 발생했습니다."));
        }
    }

}