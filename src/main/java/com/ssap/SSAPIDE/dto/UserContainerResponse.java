package com.ssap.SSAPIDE.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class UserContainerResponse {
    private String containerId;
    private String title;
    private String description;
}
