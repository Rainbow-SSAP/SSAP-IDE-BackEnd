package com.ssap.SSAPIDE.domain.member;

import com.ssap.SSAPIDE.model.Container;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank
    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    private String password;

    @NotBlank
    @Column(nullable = false, length = 30)
    private String name;

    @OneToMany(mappedBy = "user")
    private List<Container> containers;
}
