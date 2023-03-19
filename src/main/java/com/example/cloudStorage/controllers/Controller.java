package com.example.cloudStorage.controllers;

import com.example.cloudStorage.models.FileEntity;
import com.example.cloudStorage.models.FileSending;
import com.example.cloudStorage.services.FileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;


@RestController
public class Controller {

    private FileService fileService;

    public Controller(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(path = "/file", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE})
    public String uploadFile(@RequestHeader("auth-token") String token, @ModelAttribute FileSending fileSending) throws IOException {
        fileService.saveFile(token, fileSending);
        String filename = fileSending.getFilename();
        System.out.println(filename);
        System.out.println(fileSending.getFile());
        return "done";
    }

    @GetMapping(path = "/file")
    public ResponseEntity<byte[]> downloadFile(@RequestHeader("auth-token") String token,
                                                 @RequestParam("filename") String filename) {
        FileEntity fileEntity = fileService.getFile(token, filename).get(); // TODO: check is empty

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getFilename() + "\"")
                .contentType(MediaType.valueOf(fileEntity.getContentType()))
                .body(fileEntity.getData());
    }










    @PostMapping("/filee")
    public String uploadFilee(@RequestHeader("auth-token") String token,
                             MultipartHttpServletRequest mrequest,
//                             @RequestParam("filename") String filename,
                             @RequestParam("file") MultipartFile file) {
        String filename = mrequest.getParameter("filename");
        System.out.println(filename);
        System.out.println(token);
        System.out.println(file);
        return "done";
    }

    @PostMapping(path = "/file3")
    public String uploadFileQuery(@RequestHeader("auth-token") String token,
                              @RequestParam("file") MultipartFile file,
                              @RequestParam("filename") String filename) {
        System.out.println(filename);
        System.out.println(token);
        System.out.println(file);
        return "done";
    }

    @PostMapping(path = "/file4")
    public String uploadFileQuery(@RequestParam("file") MultipartFile file,
                                  @RequestParam("filename") String filename) {
        System.out.println(filename);
        System.out.println(file);
        return "done";
    }

    @PostMapping(path = "/file2", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE})
    public String uploadFile2(@RequestHeader("auth-token") String token,
                              @ModelAttribute FileSending fileSending) {
        String filename = fileSending.getFilename();
        System.out.println(filename);
        System.out.println(token);
        System.out.println(fileSending.getFile());
        return "done";
    }

}
