package com.ssap.SSAPIDE.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContainerRequestDto {
    @NotBlank(message = "Null을 포함한 빈 문자열을 허용하지 않는다.")
    @Size(min = 1, max = 20, message = "제목은 20자 이내로 작성해야 한다.")
    private String title;
    @Size(max = 500, message = "설명은 500자 이내로 작성해야 한다.")
    private String description;
    @NotNull(message = "Stack은 Null을 허용하지 않는다")
    private String stack;
    private String customControl;
}
