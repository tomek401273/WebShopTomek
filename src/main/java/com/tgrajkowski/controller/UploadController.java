package com.tgrajkowski.controller;

import com.tgrajkowski.file.convert.FileConverter;
import com.tgrajkowski.model.file.Photo;
import com.tgrajkowski.model.model.dao.FileDao;
import com.tgrajkowski.model.model.dao.UploadDao;
import com.tgrajkowski.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


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
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile multipartFile) {
        String message;
        try {
            byte[] picInBytes = multipartFile.getBytes();

            multipartFile.getInputStream().read();
            multipartFile.getInputStream().close();

            Photo photo = new Photo();
            photo.setFilteByte(picInBytes);

            String fileName = multipartFile.getOriginalFilename();
            int lastDot = fileName.lastIndexOf(".");
            String photoName = fileName.substring(0, lastDot);
            String suffix = fileName.substring(lastDot + 1, fileName.length());
            photo.setContentType(suffix);
            photo.setName(photoName);

            fileDao.save(photo);

            message = "You successfully uploaded " + multipartFile.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.OK).body(message);
        } catch (Exception e) {
            message = "FAIL to upload " + multipartFile.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }



//    @GetMapping("/getallfiles")
//    public ResponseEntity<List<String>> getListFiles(Model model) {
//        List<String> fileNames = files
//                .stream().map(fileName -> MvcUriComponentsBuilder
//                        .fromMethodName(UploadController.class, "getFile", fileName).build().toString())
//                .collect(Collectors.toList());
//
//        return ResponseEntity.ok().body(fileNames);
//    }
//
//    @GetMapping("/files/{filename:.+}")
//    @ResponseBody
//    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
//        Resource file = storageService.loadFile(filename);
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
//                .body(file);
//    }
}
