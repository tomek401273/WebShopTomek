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
        File file = fileDao.findByName(name);
        return file;
    }
}
