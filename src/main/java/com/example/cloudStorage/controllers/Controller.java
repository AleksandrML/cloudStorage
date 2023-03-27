package com.example.cloudStorage.controllers;

import com.example.cloudStorage.exceptions.FilenameError;
import com.example.cloudStorage.models.*;
import com.example.cloudStorage.services.FileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
public class Controller {

    private FileService fileService;

    public Controller(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(path = "/file", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity uploadFile(@RequestHeader("auth-token") String token, @ModelAttribute FileSending fileSending) throws IOException {
        fileService.saveFile(token, fileSending);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/file")
    public ResponseEntity<byte[]> downloadFile(@RequestHeader("auth-token") String token,
                                                 @RequestParam("filename") String filename) {
        Optional<FileEntity> fileEntityOptional = fileService.getFile(token, filename);
        if (fileEntityOptional.isEmpty()) {
            throw new FilenameError("the file %s does not exist".formatted(filename));
        }
        FileEntity fileEntity = fileEntityOptional.get();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getFilename() + "\"")
                .contentType(MediaType.valueOf(fileEntity.getContentType()))
                .body(fileEntity.getData());
    }

    @DeleteMapping(path = "/file")
    public ResponseEntity deleteFile(@RequestHeader("auth-token") String token,
                             @RequestParam("filename") String filename) {
        if (fileService.deleteFile(token, filename) > 0) {
            return ResponseEntity.ok().build();
        }
        throw new FilenameError("the file %s does not exist".formatted(filename));
    }

    @PutMapping(path = "/file")
    public ResponseEntity updateFilename(@RequestHeader("auth-token") String token,
                           @RequestParam("filename") String filenameOld, @RequestBody FileNewName fileNewName) {
        fileService.updateFilename(token, filenameOld, fileNewName.getFilename());
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/list")
    public List<FileEntityShorten> getFileList(@RequestHeader("auth-token") String token,
                                                @RequestParam("limit") int limit) {
        return fileService.getFileList(token, limit);
    }

}
