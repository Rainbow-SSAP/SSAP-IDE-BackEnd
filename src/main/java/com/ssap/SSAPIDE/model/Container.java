package com.ssap.SSAPIDE.model;

import com.ssap.SSAPIDE.domain.member.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Container {

    @Id
    private String containerId;

    private String title;
    private String description;
    private String stack;
    private String customControl;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "container")
    private List<FileAndFolder> filesAndFolders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private User user;
}