package com.tgrajkowski.controller;

import com.tgrajkowski.model.location.country.CountryDto;
import com.tgrajkowski.model.location.response.view.result.location.address.Address;
import com.tgrajkowski.model.location.response.view.result.location.address.AddressDto;
import com.tgrajkowski.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/location")
@CrossOrigin("*")
public class LocationController {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LocationService locationService;

    @RequestMapping(method = RequestMethod.GET, value = "/updateDatabase")
    public void getAllCountrysOnTheWold() {
        locationService.getAllCountryFromServer();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/country")
    public List<CountryDto> getAllCountryFromDatabase() {
        return locationService.getCountriesListFromDatabase();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/check")
    public AddressDto searchLocation(@RequestParam String search) {
      return locationService.searchLocation(search);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/country/approved")
    public List<String> getApprovedCountry() {
        return locationService.getApprovedCountry();
    }
}
