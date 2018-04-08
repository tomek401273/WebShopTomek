package com.tgrajkowski.controller;

import com.tgrajkowski.model.File;
import com.tgrajkowski.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/file")
@CrossOrigin("*")
public class FileController {
    @Autowired
    private FileService fileService;
    @RequestMapping(value = "/{name}")
    public ResponseEntity<byte[]> getFile(@PathVariable(value = "name", required = true) String name) {
       File file= fileService.getFile(name);
       if(file== null){
           return (ResponseEntity<byte[]>) ResponseEntity.notFound();
       }
      return ResponseEntity.ok().contentLength(file.getFilteByte().length).contentType(MediaType.parseMediaType(file.getContentType()))
              .body(file.getFilteByte());
    }
}
