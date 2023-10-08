package com.ssap.SSAPIDE.dto;

import com.ssap.SSAPIDE.model.Container;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ContainerDetailsResponseDto {
    private String containerId;
    private String title;
    private String description;
    private String stack;
    private String customControl;
    private LocalDateTime createdAt;

    public ContainerDetailsResponseDto(Container container) {
        this.containerId = container.getContainerId();
        this.title = container.getTitle();
        this.description = container.getDescription();
        this.stack = container.getStack();
        this.customControl = container.getCustomControl();
        this.createdAt = container.getCreatedAt();
    }
}