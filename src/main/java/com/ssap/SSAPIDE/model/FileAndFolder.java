package com.ssap.SSAPIDE.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileAndFolder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "containerId")
    private Container container;

    private Long parentFolderId; // 부모 폴더Id
    private String name;  // 폴더 또는 파일명
    private Boolean type; // true: 파일, false: 폴더
    private String ext;
    private String content; // 파일 내용
    private LocalDateTime lastModified; // 수정 시간
    private String path; // 파일 또는 폴더의 경로
}