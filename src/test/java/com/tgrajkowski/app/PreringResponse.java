package com.tgrajkowski.app;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PreringResponse {

    @Test
    public void prparingResponse() {
        String citys = "[\"Torbert, LA, United States\",\"Torch, OH, United States\",\"Tornado, WV, United States\",\"Tornillo, TX, United States\",\"Toronto, KS, United States\",\"Toronto, OH, United States\",\"Toronto, SD, United States\",\"Torrance, CA, United States\",\"Torrance, PA, United States\",\"Torreon, NM, United States\",\"Torrey, UT, United States\",\"Torrington, CT, United States\",\"Torrington, WY, United States\",\"Tortilla Flat, AZ, United States\"]";
        List<String> cityList = new ArrayList<>();
        citys = citys.substring(1, citys.length());
        while (citys.length()>0) {
            citys = citys.substring(1, citys.length());
            String subString = citys.substring(0, citys.indexOf("\""));
            citys = citys.replace(subString, "");
            subString = subString.substring(0, subString.lastIndexOf(','));
            cityList.add(subString);
            citys = citys.substring(2, citys.length());
        }

        System.out.println("CityLISt dd");
        for (String city: cityList) {
            System.out.println(city);
        }


    }


}
