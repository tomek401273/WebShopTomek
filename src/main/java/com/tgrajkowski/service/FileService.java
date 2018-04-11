package com.tgrajkowski.service;

import com.tgrajkowski.model.File;
import com.tgrajkowski.model.model.dao.FileDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    @Autowired
    protected FileDao fileDao;

    public File getFile(String name) {
        System.out.println("file service: "+name);
        File file = fileDao.findByName(name);
        System.out.println("File id: "+file.getId());
        System.out.println("File content type: "+file.getContentType());
        System.out.println("File name: "+file.getName());
        System.out.println("File byte: "+file.getFilteByte());
        return file;
    }
}
