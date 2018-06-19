package com.tgrajkowski.service;

import com.tgrajkowski.model.location.LocationAddress;
import com.tgrajkowski.model.location.response.Response;
import com.tgrajkowski.model.location.response.view.View;
import com.tgrajkowski.model.location.response.view.result.Result;
import com.tgrajkowski.model.location.response.view.result.location.Location;
import com.tgrajkowski.model.location.response.view.result.location.address.Address;
import org.junit.Test;

public class SimpleTest {

    @Test
    public void justTest() {
        Address address = new Address();
        address.setCity("City City City");
        Location location = new Location();
        location.setAddress(address);
        Result result = new Result();
        result.setLocation(location);
        Result[] results = new Result[1];
        results[0] = result;
        View view = new View("type", 1, results);
        View[] views = new View[1];
        views[0] = view;
        Response response = new Response(views);
        LocationAddress locationAddress = new LocationAddress(response);
//        locationAddress.setResponse(new Response());
//        locationAddress.getResponse().setView(new View[2]);
//        locationAddress.getResponse().getView()[1]= new View();
        System.out.println("resss: "+locationAddress.getResponse().getView().length);
//        locationAddress.getResponse().getView()[0].getResult().length;
        System.out.println(locationAddress.getResponse().getView()[0].getResult().length);
        System.out.println(locationAddress.getResponse().getView()[0].getResult()[0].getLocation().getAddress().getCity());

    }
}
