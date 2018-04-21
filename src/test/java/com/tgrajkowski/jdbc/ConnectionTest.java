package com.tgrajkowski.jdbc;

import com.tgrajkowski.service.jdbc.DbManager;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionTest {
//    @Test
//    public void testConnect() throws SQLException {
//        //Given
//        //When
//        DbManager dbManager = DbManager.getInstance();
//        //When
//        String sqlQuery = "SELECT * FROM role";
//        Statement statement = dbManager.getConnection().createStatement();
//        ResultSet rs = statement.executeQuery(sqlQuery);
//
//        //Then
//        int counter = 0;
//        while(rs.next()) {
//            System.out.println(rs.getInt("id") + ", " +
//                    rs.getString("code") + ", " +
//                    rs.getString("name"));
//            counter++;
//        }
//        rs.close();
//        statement.close();
//        Assert.assertEquals(2, counter);
//    }
//
//    @Test
//    public void uploadImage() throws SQLException {
//        File file = new File("/home/tomasz/Obrazy/petru12.jpg");
//        FileInputStream fis = null;
//        try {
//            fis = new FileInputStream(file);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        DbManager dbManager = DbManager.getInstance();
//        //When
////        String sqlQuery = "INSERT INTO upload (id, filte_byte, name VALUES (1,";
////        Statement statement = dbManager.getConnection().createStatement();
//
//       PreparedStatement ps2= dbManager.getConnection().prepareStatement("INSERT INTO upload (id, file_byte) VALUES (?, lo_from_bytea(0, ?))");
//       ps2.setLong(1, (long)1);
//       ps2.setBinaryStream(2, fis, (int)file.length());
//       ps2.executeUpdate();
//       ps2.close();
////        PreparedStatement ps = conn.prepareStatement("INSERT INTO images VALUES (?, ?)");
////        ps.setString(1, file.getName());
////        ps.setBinaryStream(2, fis, (int)file.length());
////        ps.executeUpdate();
////        ps.close();
////        fis.close();
//    }
//
//    @Test
//    public void uploadImageToFileTable() throws SQLException {
//        File file = new File("/home/tomasz/Obrazy/petru12.jpg");
//        FileInputStream fis = null;
//        try {
//            fis = new FileInputStream(file);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        DbManager dbManager = DbManager.getInstance();
//        //When
////        String sqlQuery = "INSERT INTO upload (id, filte_byte, name VALUES (1,";
////        Statement statement = dbManager.getConnection().createStatement();
//
////        PreparedStatement ps2= dbManager.getConnection().prepareStatement("INSERT INTO upload (id, file_byte) VALUES (?, lo_from_bytea(0, ?))");
//        PreparedStatement ps2= dbManager.getConnection().prepareStatement("INSERT INTO file (id, content_type, filte_byte,  name ) VALUES (?, ?, lo_from_bytea(0, ?), ?)");
//        ps2.setLong(1, (long)5);
//        ps2.setString(2, ".jppg");
//        ps2.setBinaryStream(3, fis, (int)file.length());
//        ps2.setString(4, "petruP");
//        ps2.executeUpdate();
//        ps2.close();
////        PreparedStatement ps = conn.prepareStatement("INSERT INTO images VALUES (?, ?)");
////        ps.setString(1, file.getName());
////        ps.setBinaryStream(2, fis, (int)file.length());
////        ps.executeUpdate();
////        ps.close();
////        fis.close();
//    }
}
//id | content_type | filte_byte |  name