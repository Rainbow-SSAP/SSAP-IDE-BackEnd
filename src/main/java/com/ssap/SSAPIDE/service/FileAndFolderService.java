package com.ssap.SSAPIDE.service;

import com.ssap.SSAPIDE.dto.FileAndFolderCreateRequestDto;
import com.ssap.SSAPIDE.model.Container;
import com.ssap.SSAPIDE.model.FileAndFolder;
import com.ssap.SSAPIDE.repository.FileAndFolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FileAndFolderService {

    private final FileAndFolderRepository fileAndFolderRepository;
    private final ContainerService containerService;

    public String createFileOrFolder(FileAndFolderCreateRequestDto dto) {
        Container container = containerService.getContainerById(dto.getContainerId());

        FileAndFolder fileAndFolder = new FileAndFolder();
        fileAndFolder.setContainer(container);
        fileAndFolder.setParentFolderId(dto.getParentFolderId());
        fileAndFolder.setName(dto.getName());
        fileAndFolder.setType(dto.getType());
        fileAndFolder.setContent(dto.getContent());
        fileAndFolder.setLastModified(LocalDateTime.now());
        fileAndFolder.setPath(dto.getPath());

        fileAndFolderRepository.save(fileAndFolder);
        return fileAndFolder.getName();
    }
}