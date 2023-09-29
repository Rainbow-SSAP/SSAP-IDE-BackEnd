package com.ssap.SSAPIDE.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContainerResponseDto {
    private String containerId;
    private LocalDateTime createdAt;
}
