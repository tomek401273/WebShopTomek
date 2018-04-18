package com.tgrajkowski.service;

import com.google.gson.Gson;
import com.tgrajkowski.model.location.HeaderRequestInterceptor;
import com.tgrajkowski.model.location.LocationAddress;
import com.tgrajkowski.model.location.country.*;
import com.tgrajkowski.model.location.response.view.result.location.Location;
import com.tgrajkowski.model.location.response.view.result.location.address.Address;
import com.tgrajkowski.model.location.response.view.result.location.address.AddressDto;
import com.tgrajkowski.model.location.response.view.result.location.address.AddressMapper;
import com.tgrajkowski.model.model.dao.CountryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class LocationService {
    @Autowired
    private CountryMapper countryMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CountryDao countryDao;

    @Autowired
    private AddressMapper addressMapper;

    public void getAllCountryFromServer() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("X-Mashape-Key", "us7DCMbPDWmshXfiu9KNu65k5uFBp1fmAZljsnxoPKgQczv9BJ");
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        URI uri = UriComponentsBuilder.fromHttpUrl("https://restcountries-v1.p.mashape.com/all")
                .build().encode().toUri();


        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        interceptors.add(new HeaderRequestInterceptor("Accept", MediaType.APPLICATION_JSON_VALUE));
        interceptors.add(new HeaderRequestInterceptor("X-Mashape-Key", "us7DCMbPDWmshXfiu9KNu65k5uFBp1fmAZljsnxoPKgQczv9BJ"));
        restTemplate.setInterceptors(interceptors);

        CountryDto[] countries = restTemplate.getForObject(uri, CountryDto[].class);
        for (CountryDto countryDto : countries) {
            System.out.println(countryDto.toString());
            Country country = countryMapper.mapToCountry(countryDto);
            countryDao.save(country);
        }

//        List<Country> countryList = countryMapper.mapToCountryDtoList(countries);
//        if (countryDao.count()==0) {
//            System.out.println("Saving data to database");
//            countryDao.save(countryList);
//        }
    }

//    public void getAllCountryFromServer() {
//        URI uri = UriComponentsBuilder.fromHttpUrl("https://battuta.medunes.net/api/country/all/?key=8e851b15aa924e14d191aad1e00afe7a")
//                .build().encode().toUri();
//        CountryDto[] countryDtos = restTemplate.getForObject(uri, CountryDto[].class);
//        for (CountryDto countryDto : countryDtos) {
//            Country country = countryMapper.mapToCountry(countryDto);
//            countryDao.save(country);
//            System.out.println(countryDto.toString());
//        }
//    }

    public void getAllRegionFroServer() {
        URI uri = UriComponentsBuilder.fromHttpUrl("https://battuta.medunes.net/api/region/pl/all/?key=8e851b15aa924e14d191aad1e00afe7a")
                .build().encode().toUri();
        Region[] regions = restTemplate.getForObject(uri, Region[].class);
        for (Region region : regions) {
            System.out.println(region.toString());
        }
    }

    public void getAllCityFroServer() {
        URI uri = UriComponentsBuilder.fromHttpUrl("https://battuta.medunes.net/api/city/pl/search/?region=Wojewodztwo Kujawsko-Pomorskie&key=8e851b15aa924e14d191aad1e00afe7a")
                .build().encode().toUri();
        CityDto[] regions = restTemplate.getForObject(uri, CityDto[].class);
        for (CityDto region : regions) {
            System.out.println(region.toString());
        }
    }

    public AddressDto searchLocation(String search) {
        System.out.println("search: " + search);
        URI url = UriComponentsBuilder.fromHttpUrl("https://geocoder.cit.api.here.com/6.2/geocode.json")
                .queryParam("searchtext", search)
                .queryParam("app_id", "ZurixIlHeTvNn84GbCkq")
                .queryParam("app_code", "sw87CS8QKIoFiJ4vfa8r3A")
                .build().encode().toUri();

        LocationAddress locationAddress = restTemplate.getForObject(url, LocationAddress.class);
        Address address = locationAddress.getResponse().getView()[0].getResult()[0].getLocation().getAddress();
        AddressDto addressDto = addressMapper.mapToAddresDto(address);

        String matchesLevel = locationAddress.getResponse().getView()[0].getResult()[0].getMatchLevel();
        System.out.println("matchesLevel: "+matchesLevel);
        System.out.println("address: "+address.toString());

        if (matchesLevel.equals("street") || matchesLevel.equals("district") || matchesLevel.equals("houseNumber")) {
            return addressDto;
        }

        return null;
    }

    public void deleteAllCountry() {
        countryDao.deleteAll();
    }

    public List<CountryDto> getCountriesListFromDatabase() {
        List<Country> countryList = countryDao.findAll();
        return countryMapper.mapToCountryDtoList(countryList);
    }

    public void getCity(String countryAlpha2Code, String city) {

        URI url = UriComponentsBuilder.fromHttpUrl("http://gd.geobytes.com/AutoCompleteCity")
                .queryParam("filter", countryAlpha2Code)
                .queryParam("q", city)
                .build().encode().toUri();

//        String citys = restTemplate.getForObject(url
//                , String.class);
//        List<String> cityList = new ArrayList<>();
//        citys = citys.substring(1, citys.length());
//        while (citys.length() > 0) {
//            citys = citys.substring(1, citys.length());
//            String subString = citys.substring(0, citys.indexOf("\""));
//            citys = citys.replace(subString, "");
//            subString = subString.substring(0, subString.lastIndexOf(','));
//            cityList.add(subString);
//            citys = citys.substring(2, citys.length());
//        }
//        for (String cityTemp : cityList) {
//            System.out.println(cityTemp);
//        }
    }

    public List<String> getApprovedCountry() {
        List<String> approvedCountry = new ArrayList<>();
        List<Country> countryList = countryDao.findByApproved(true);
        for (Country country : countryList) {
            approvedCountry.add(country.getAlpha2Code());
        }
        return approvedCountry;
    }
}
