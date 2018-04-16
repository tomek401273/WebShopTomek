package com.tgrajkowski.controller;

import com.tgrajkowski.model.File;
import com.tgrajkowski.model.model.dao.FileDao;
import com.tgrajkowski.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.*;

@Controller
@RequestMapping("/file")
@CrossOrigin("*")
public class FileController {
    @Autowired
    private FileService fileService;


//    @RequestMapping(method = RequestMethod.GET, value = "/file/{name}")
//    public ResponseEntity<byte[]> getFile(@PathVariable(value = "name", required = true) String name) throws IOException {
//        System.out.println("Name name: " + name);
//        File file = fileService.getFile(name);
//        if (file == null) {
//            return (ResponseEntity<byte[]>) ResponseEntity.notFound();
//        }
////        ResponseEntity<byte[]> responseEntity = ResponseEntity.ok().contentLength(file.getFilteByte().length).contentType(MediaType.parseMediaType(file.getContentType()))
////                .body(file.getFilteByte());
//
//        return ResponseEntity.ok().contentLength(file.getFilteByte().length).contentType(MediaType.parseMediaType(file.getContentType()))
//                .body(file.getFilteByte());
//    }


//    @RequestMapping("/file/{name}")
//    public ResponseEntity<byte[]> downloadFile(@PathVariable("name") String name) throws IOException {
//        System.out.println("Name name: " + name);
//        String filename = "/home/tomasz/Obrazy/" + name + ".jpg";
//        InputStream inputImage = new FileInputStream(filename);
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        byte[] buffer = new byte[512];
//        int l = inputImage.read(buffer);
//        while (l >= 0) {
//            outputStream.write(buffer, 0, l);
//            l = inputImage.read(buffer);
//        }
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Content-Type", "image/jpeg");
//        headers.set("Content-Disposition", "attachment; filename=\"" + name + ".jpg\"");
//        return new ResponseEntity<byte[]>(outputStream.toByteArray(), headers, HttpStatus.OK);
//    }

    @RequestMapping("/file/{name}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("name") String name) throws IOException {
        System.out.println("Name name: " + name);
//        String filename = "/home/tomasz/Obrazy/" + name + ".jpg";
//        InputStream inputImage = new FileInputStream(filename);
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // dodaj optionala na tym
        File file = fileService.getFile(name);

//        byte[] buffer = new byte[512];
//        int l = inputImage.read(buffer);


//        while (l >= 0) {
//            outputStream.write(buffer, 0, l);
//            l = inputImage.read(buffer);
//        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "image/jpeg");
        headers.set("Content-Disposition", "attachment; filename=\"" + name + ".jpg\"");
// dla obrazków nie dodawaj
       ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(file.getFilteByte(), headers, HttpStatus.OK);


//
//        ResponseEntity<byte[]> responseEntity =ResponseEntity
//                .ok()
//                .contentLength(file.getFilteByte().length)
//                .contentType(MediaType.parseMediaType(file.getContentType()))
//                .headers(headers)
//                .body(file.getFilteByte());

//        return new ResponseEntity<byte[]>(outputStream.toByteArray(), headers, HttpStatus.OK);
        return responseEntity;
    }
}
