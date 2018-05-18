package com.tgrajkowski.file.convert;


import com.tgrajkowski.model.model.dao.FileDao;
import com.tgrajkowski.service.jdbc.DbManager;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class FileConverter {

    public File convert(MultipartFile file) throws SQLException {
        File convFile = new File(file.getOriginalFilename());
//        try {
//            convFile.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(convFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

//        try {
//            fos.write(file.getBytes());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        saveFileToDatabase(convFile);
        return convFile;
    }

//    public void saveFileToDatabase(File file) throws SQLException {
////        Photo file = new Photo("/home/tomasz/Obrazy/petru12.jpg");
//        FileInputStream fis = null;
//        try {
//            fis = new FileInputStream(file);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        DbManager dbManager = DbManager.getInstance();
//        PreparedStatement ps2= dbManager.getConnection().prepareStatement("INSERT INTO photo (id, content_type, filte_byte,  name ) VALUES (?, ?, lo_from_bytea(0, ?), ?)");
//        ps2.setLong(1, (long)6);
//        ps2.setString(2, ".jppg2");
//        ps2.setBinaryStream(3, fis, (int)file.length());
//        ps2.setString(4, "petruP2");
//        ps2.executeUpdate();
//        ps2.close();
//    }









//    public String encodeFileToBase64Binary(File file){
//        String encodedfile = null;
//        try {
//            FileInputStream fileInputStreamReader = new FileInputStream(file);
//            byte[] bytes = new byte[(int)file.length()];
//            fileInputStreamReader.read(bytes);
//            encodedfile = new String(Base64.encodeBase64(bytes), "UTF-8");
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        return encodedfile;
//    }
}
