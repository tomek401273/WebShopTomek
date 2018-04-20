package com.tgrajkowski.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.tgrajkowski.file.convert.FileConverter;
import com.tgrajkowski.model.file.Photo;
import com.tgrajkowski.model.model.dao.FileDao;
import com.tgrajkowski.model.model.dao.UploadDao;
import com.tgrajkowski.service.StorageService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;


@Controller
@RequestMapping("/upload")
@CrossOrigin("*")
public class UploadController {

    @Autowired
    StorageService storageService;

    @Autowired
    private UploadDao uploadDao;

    List<String> files = new ArrayList<String>();

    private FileConverter fileConverter = new FileConverter();

    @Autowired
    private FileDao fileDao;

    @PostMapping("/post")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        System.out.println("upload");
        String message = "";
        try {
            storageService.store(file);
            files.add(file.getOriginalFilename());
//			InputStream inputStream = new ByteArrayInputStream(byteArr);
//			byte[] bytes = file.getBytes();
//			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new Photo("spock")));
//			stream.write(bytes);
//			stream.close();

//			byte [] byteArr=file.getBytes();
//			InputStream inputStream = new ByteArrayInputStream(byteArr);
//			inputStream.read(byteArr);
//			inputStream.close();

//            String base64 = fileConverter.encodeFileToBase64Binary(fileConverter.convert(file));
//            System.out.println("Start: ");
//            base64 = base64.replace("[", "");
//            System.out.println(base64);
//            System.out.println("Stop");
//            fileDao.savePhotoBase64((long)4, base64, "spock4");

            File convertedFile = fileConverter.convert(file);

            byte[] picInBytes = new byte[(int) convertedFile.length()];
            FileInputStream fileInputStream = new FileInputStream(convertedFile);
            fileInputStream.read(picInBytes);
            fileInputStream.close();
            Photo photo = new Photo();
            photo.setId((long)10);
            photo.setContentType("jpg");
            photo.setFilteByte(picInBytes);
            String photoName = convertedFile.getName();
            photoName = photoName.replace(".jpg", "");
            photo.setName(photoName);
            fileDao.save(photo);

            message = "You successfully uploaded " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "FAIL to upload " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }


    @GetMapping("/getallfiles")
    public ResponseEntity<List<String>> getListFiles(Model model) {
        List<String> fileNames = files
                .stream().map(fileName -> MvcUriComponentsBuilder
                        .fromMethodName(UploadController.class, "getFile", fileName).build().toString())
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(fileNames);
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }
}
