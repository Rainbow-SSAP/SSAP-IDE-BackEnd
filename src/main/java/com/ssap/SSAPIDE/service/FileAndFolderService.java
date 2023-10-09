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

        if (dto.getType()) { // 파일인 경우
            fileAndFolder.setContent(dto.getContent());
            fileAndFolder.setExt(dto.getExt());
        }

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

    public void renameFolder(String containerId, Long folderId, String newFolderName) {
        Optional<FileAndFolder> optionalFolder = fileAndFolderRepository.findById(folderId);
        if (optionalFolder.isPresent()) {
            FileAndFolder folderToRename = optionalFolder.get();
            if (!folderToRename.getContainer().getContainerId().equals(containerId)) {
                throw new SecurityException("동일한 이름의 폴더가 이미 해당 경로에 존재합니다.");
            }

            if (fileAndFolderRepository.existsByNameAndPath(newFolderName, folderToRename.getPath())) {
                throw new SecurityException("동일한 이름의 폴더가 이미 해당 경로에 존재합니다.");
            }

            folderToRename.setName(newFolderName);
            fileAndFolderRepository.save(folderToRename);
        } else {
            throw new NoSuchElementException("지정된 경로에 해당하는 폴더가 존재하지 않습니다.");
        }
    }

    public void deleteFile(String containerId, Long folderId) {
        Optional<FileAndFolder> optionalFolder = fileAndFolderRepository.findById(folderId);
        if (optionalFolder.isPresent()) {
            FileAndFolder folderToDelete = optionalFolder.get();
            if (!folderToDelete.getContainer().getContainerId().equals(containerId)) {
                throw new SecurityException("해당 파일를 삭제할 권한이 없습니다.");
            }
            fileAndFolderRepository.delete(folderToDelete);
        } else {
            throw new NoSuchElementException("지정된 경로에 해당하는 파일이 존재하지 않습니다.");
        }
    }

    public void renameFile(String containerId, Long folderId, String newFolderName) {
        Optional<FileAndFolder> optionalFolder = fileAndFolderRepository.findById(folderId);
        if (optionalFolder.isPresent()) {
            FileAndFolder folderToRename = optionalFolder.get();
            if (!folderToRename.getContainer().getContainerId().equals(containerId)) {
                throw new SecurityException("동일한 이름의 파일이 이미 해당 경로에 존재합니다.");
            }

            if (fileAndFolderRepository.existsByNameAndPath(newFolderName, folderToRename.getPath())) {
                throw new SecurityException("동일한 이름의 파일이 이미 해당 경로에 존재합니다.");
            }

            folderToRename.setName(newFolderName);
            fileAndFolderRepository.save(folderToRename);
        } else {
            throw new NoSuchElementException("지정된 경로에 해당하는 파일이 존재하지 않습니다.");
        }
    }
}