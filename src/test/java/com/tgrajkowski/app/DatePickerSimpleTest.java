package com.tgrajkowski.app;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

public class DatePickerSimpleTest {
    @Test
    public void justTest() throws ParseException {
        String inputDateFrom = "23/04/2018";
        String appendix = " 23:59:59";
        int prefixInt = Integer.parseInt(inputDateFrom.subSequence(0, 2).toString());
        System.out.println("inputDateFrom: " + inputDateFrom);
        System.out.println("prefixInt: " + prefixInt);
        int prefixIntMinus = --prefixInt;
        String prefixFromated = String.valueOf(prefixIntMinus);
        System.out.println("prefixIntFormated: " + prefixFromated);

        inputDateFrom = inputDateFrom + appendix;
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date dateFormated = dateFormat.parse(inputDateFrom);
        System.out.println(dateFormated);
    }

    @Test
    public void testTest() throws ParseException {
        Date created = new Date();
        String parsedCreated;
        System.out.println(created);
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        parsedCreated = dateFormat.format(created);
        System.out.println(parsedCreated);
    }

    @Test
    public void justTest2() {
        String inputDateFrom = "23/04/2018";
        inputDateFrom = inputDateFrom.replaceAll("/", "-");
        System.out.println("formated: " + inputDateFrom);
        String day = inputDateFrom.substring(0, 2);
        String month = inputDateFrom.substring(2, 6);
        String year = inputDateFrom.substring(6, inputDateFrom.length());
        inputDateFrom = year + month + day;
        System.out.println("day: " + day);
        System.out.println("month: " + month);
        System.out.println("year: " + year);
        System.out.println(inputDateFrom);
    }

    @Test
    public void justTest3() {
        String a = "23/04/2018";
        String b = "3/04/2018";
        System.out.println("length a: "+a.length());
        System.out.println("length b: "+b.length());
        System.out.println("length: "+"1234".length());
    }

    @Test
    public void justTest4() {
    Date date = calculateDateTomorow();
    }
    public Date calculateDateTomorow() {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, 10);
        return calendar.getTime();
    }

    @Test
    public void justTest5() {
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);
    }

    public Long calulateWaitingTime() {
        Date dateTomorow = calculateDateShipping();
        Long futureDate = dateTomorow.getTime();
        Long nowDate = System.currentTimeMillis();
        Long calculatedDate = futureDate - nowDate;
        return calculatedDate;
    }

    public Date calculateDateShipping() {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, 1);
        return calendar.getTime();
    }
}
