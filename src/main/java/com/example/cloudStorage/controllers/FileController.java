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
public class FileController {

    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(path = "/file", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> uploadFile(@ModelAttribute FileSending fileSending) throws IOException {
        fileService.saveFile(fileSending);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/file")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("filename") String filename) {
        Optional<FileEntity> fileEntityOptional = fileService.getFile(filename);
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
    public ResponseEntity<Void> deleteFile(@RequestParam("filename") String filename) {
        if (fileService.deleteFile(filename) > 0) {
            return ResponseEntity.ok().build();
        }
        throw new FilenameError("the file %s does not exist".formatted(filename));
    }

    @PutMapping(path = "/file")
    public ResponseEntity<Void> updateFilename(@RequestParam("filename") String filenameOld, @RequestBody FileNewName fileNewName) {
        fileService.updateFilename(filenameOld, fileNewName.getFilename());
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/list")
    public List<FileEntityShorten> getFileList(@RequestParam("limit") int limit) {
        return fileService.getFileList(limit);
    }

}
