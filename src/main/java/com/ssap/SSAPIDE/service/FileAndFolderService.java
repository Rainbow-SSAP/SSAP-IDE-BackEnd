package com.ssap.SSAPIDE.service;

import com.ssap.SSAPIDE.dto.FileAndFolderCreateRequestDto;
import com.ssap.SSAPIDE.model.Container;
import com.ssap.SSAPIDE.model.FileAndFolder;
import com.ssap.SSAPIDE.repository.FileAndFolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    public void deleteFolder(String containerId, Long folderId) {
        Optional<FileAndFolder> optionalFolder = fileAndFolderRepository.findById(folderId);
        if (optionalFolder.isPresent()) {
            FileAndFolder folderToDelete = optionalFolder.get();
            if (!folderToDelete.getContainer().getContainerId().equals(containerId)) {
                throw new SecurityException("해당 폴더를 삭제할 권한이 없습니다.");
            }
            fileAndFolderRepository.delete(folderToDelete);
        } else {
            throw new NoSuchElementException("지정된 경로에 해당하는 폴더가 존재하지 않습니다.");
        }
    }
}