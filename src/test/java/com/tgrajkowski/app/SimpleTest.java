package com.tgrajkowski.app;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class SimpleTest {
    public Date removeTime(LocalDate localDate) {
        Date date1 = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    @Test
    public void justTest() {
        LocalDate localDate = LocalDate.now();
        Date dateNow = new Date();
        System.out.println(localDate);
        localDate = localDate.plusDays(1);
        System.out.println(localDate);
        Date date2 = removeTime(localDate);
        System.out.println(date2);

    }

    public Date calculateDateTomorow() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 15);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    @Test
    public void justTest2() {
        Date date = new Date();
        System.out.println(date);
        Date date2 = calculateDateTomorow();
        System.out.println(date2);
        Long futureDate = date2.getTime();
        Long nowDate = System.currentTimeMillis();
        Long calulatedDate = futureDate - nowDate;

        convertMiliscendToNormalTime(calulatedDate);
    }

    public void convertMiliscendToNormalTime(Long milisec) {
        System.out.println("milisec: " + milisec);
        Long sec = milisec / 1000;
        System.out.println("sec: " + sec);
        Long min = sec / 60;
        Long hsec = sec % 60;
        System.out.println(" min: " + min);
        Long h = min / 60;
        Long hmin = min % 60;
        System.out.println("h: " + h + "; min: " + hmin + "; sec: " + hsec);
    }

    public void convertMiliscendToNormalTime2(Long milisec) {
    }

    @Test
    public void tetTet() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String s = bCryptPasswordEncoder.encode("tomek");
        boolean b = bCryptPasswordEncoder.matches("tomek3", s);
        System.out.println(s);
        System.out.println("Are equals passwords ???  "+ b);
    }

    @Test
    public void tetst() {
        String fileName = "spock.gif";
        int lastDot = fileName.lastIndexOf(".");

        String photoName = fileName.substring(0, lastDot);
        String suffix = fileName.substring(lastDot+1, fileName.length());
        System.out.println(fileName);
        System.out.println(photoName);
        System.out.println(suffix);
    }
}
