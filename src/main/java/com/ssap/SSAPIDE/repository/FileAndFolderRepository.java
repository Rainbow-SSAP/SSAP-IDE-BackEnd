package com.ssap.SSAPIDE.repository;

import com.ssap.SSAPIDE.model.FileAndFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileAndFolderRepository extends JpaRepository<FileAndFolder, Long> {
    boolean existsByNameAndPath(String name, String path);
}
