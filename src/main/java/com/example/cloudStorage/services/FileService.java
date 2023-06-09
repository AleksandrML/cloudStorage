package com.example.cloudStorage.services;

import com.example.cloudStorage.exceptions.FilenameError;
import com.example.cloudStorage.models.FileEntity;
import com.example.cloudStorage.models.FileEntityShorten;
import com.example.cloudStorage.models.FileSending;
import com.example.cloudStorage.repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    @Transactional
    public void saveFile(FileSending fileSending) throws IOException {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (fileRepository.findByFilenameAndUserName(fileSending.getFilename(), userName).isPresent()) {
            throw new FilenameError(
                    ("please delete firstly the existed file with the name %s before loading your " +
                            "new file with the same name").formatted(fileSending.getFilename()));
        }
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFilename(fileSending.getFilename());
        fileEntity.setContentType(fileSending.getFile().getContentType());
        fileEntity.setData(fileSending.getFile().getBytes());
        fileEntity.setSize(fileSending.getFile().getSize());
        fileEntity.setUserName(userName);
        fileRepository.save(fileEntity);
    }

    @Transactional
    public Optional<FileEntity> getFile(String filename) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return fileRepository.findByFilenameAndUserName(filename, userName);
    }

    @Transactional
    public Long deleteFile(String filename) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return fileRepository.deleteByUserNameAndFilename(userName, filename);
    }

    @Transactional
    public void updateFilename(String filenameOld, String filenameNew) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        fileRepository.updateFilename(filenameNew, userName, filenameOld);
    }

    @Transactional
    public List<FileEntityShorten> getFileList(int limit) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Pageable topN = PageRequest.of(0, limit);
        return fileRepository.findByUserNameOrderByFilename(userName, topN)
                .stream().map(it -> new FileEntityShorten(it.getFilename(), it.getSize().intValue())).toList();
    }

}
