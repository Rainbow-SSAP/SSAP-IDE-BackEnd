package com.ssap.SSAPIDE.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileRenameRequestDto {
    @NotBlank
    private String newFileName;
}
