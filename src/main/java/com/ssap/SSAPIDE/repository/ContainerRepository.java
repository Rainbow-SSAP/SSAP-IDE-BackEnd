package com.ssap.SSAPIDE.repository;

import com.ssap.SSAPIDE.model.Container;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContainerRepository extends JpaRepository<Container, String> {
    List<Container> findByUser_MemberId(Long id);
}