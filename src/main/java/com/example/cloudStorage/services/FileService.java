package com.example.cloudStorage.services;

import com.example.cloudStorage.exceptions.FilenameAlreadyExists;
import com.example.cloudStorage.models.FileEntity;
import com.example.cloudStorage.models.FileSending;
import com.example.cloudStorage.repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    @Transactional
    public void saveFile(String token, FileSending fileSending) throws IOException {
        String userName = UserService.getUserName(token);
        if (fileRepository.findByFilenameAndUserName(fileSending.getFilename(), userName).isPresent()) {
            throw new FilenameAlreadyExists(
                    ("please delete first the existed file with the name %s before loading your " +
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
    public Optional<FileEntity> getFile(String token, String filename) {
        String userName = UserService.getUserName(token);
        return fileRepository.findByFilenameAndUserName(filename, userName);
    }

    @Transactional
    public Long deleteFile(String token, String filename) {
        String userName = UserService.getUserName(token);
        return fileRepository.deleteByUserNameAndFilename(userName, filename);
    }

    @Transactional
    public void updateFilename(String token, String filenameOld, String filenameNew) {
        String userName = UserService.getUserName(token);
        fileRepository.updateFilename(filenameNew, userName, filenameOld);
    }

}
