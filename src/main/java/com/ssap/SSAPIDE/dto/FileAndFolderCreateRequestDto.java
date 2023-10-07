package com.ssap.SSAPIDE.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileAndFolderCreateRequestDto {
    @NotBlank
    private String containerId;
    @NotBlank
    private Long parentFolderId;
    @NotBlank
    private String name;
    @NotBlank
    private Boolean type;

    // 파일인 경우에만 사용
    private String ext;
    private String content;

    @NotBlank
    private String path;

}