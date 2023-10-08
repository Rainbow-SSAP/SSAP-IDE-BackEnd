package com.ssap.SSAPIDE.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileAndFolderCreateRequestDto {
    private String containerId;
    @NotNull
    private Long parentFolderId;
    @NotBlank
    private String name;
    @NotNull
    private Boolean type;

    // 파일인 경우에만 사용
    private String ext;
    private String content;

    @NotBlank
    private String path;

}