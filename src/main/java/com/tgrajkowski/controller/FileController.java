package com.tgrajkowski.controller;

import com.tgrajkowski.model.file.Photo;
import com.tgrajkowski.model.model.dao.UploadDao;
import com.tgrajkowski.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.*;
import java.util.Optional;

@Controller
@RequestMapping("/file")
@CrossOrigin("*")
public class FileController {
    @Autowired
    private FileService fileService;

    @Autowired
    private UploadDao uploadDao;


    @RequestMapping("/file/{name}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("name") String name) throws IOException {
        System.out.println("Name name: " + name);
        name = name.replace(".jpg", "");
        System.out.println("Name converted: " + name);

        //   InputStream inputImage = new FileInputStream(filename);
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Optional<Photo> file = Optional.ofNullable(fileService.getFile(name));

//        byte[] buffer = new byte[512];
//        int l = inputImage.read(buffer);
//        while (l >= 0) {
//            outputStream.write(buffer, 0, l);
//            l = inputImage.read(buffer);
//        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "image/jpeg");
        // headers.set("Content-Disposition", "attachment; filename=\"" + name + ".jpg\"");
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(file.get().getFilteByte(), headers, HttpStatus.OK);
        return responseEntity;
    }
}
