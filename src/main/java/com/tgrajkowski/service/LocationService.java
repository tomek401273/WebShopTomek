package com.tgrajkowski.service;

import com.google.gson.Gson;
import com.tgrajkowski.model.location.LocationAddress;
import com.tgrajkowski.model.location.country.*;
import com.tgrajkowski.model.location.response.view.result.location.Location;
import com.tgrajkowski.model.model.dao.CountryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
public class LocationService {
    @Autowired
    private CountryMapper countryMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CountryDao countryDao;

//    public void getAllCountryFromServer() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//        headers.set("X-Mashape-Key", "us7DCMbPDWmshXfiu9KNu65k5uFBp1fmAZljsnxoPKgQczv9BJ");
//        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
//        URI uri = UriComponentsBuilder.fromHttpUrl("https://restcountries-v1.p.mashape.com/all")
//                .build().encode().toUri();
//
//
//        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
//        interceptors.add(new HeaderRequestInterceptor("Accept", MediaType.APPLICATION_JSON_VALUE));
//        interceptors.add(new HeaderRequestInterceptor("X-Mashape-Key", "us7DCMbPDWmshXfiu9KNu65k5uFBp1fmAZljsnxoPKgQczv9BJ"));
//        restTemplate.setInterceptors(interceptors);
//
//        CountryDto[] countries = restTemplate.getForObject(uri, CountryDto[].class);
//
//        List<Country> countryList = countryMapper.mapToCountryDtoList(countries);
//        if (countryDao.count()==0) {
//            System.out.println("Saving data to database");
//            countryDao.save(countryList);
//        }
//    }

    public void getAllCountryFromServer() {
        URI uri = UriComponentsBuilder.fromHttpUrl("https://battuta.medunes.net/api/country/all/?key=8e851b15aa924e14d191aad1e00afe7a")
                .build().encode().toUri();
        CountryDto[] countryDtos = restTemplate.getForObject(uri, CountryDto[].class);
        for (CountryDto countryDto : countryDtos) {
            System.out.println(countryDto.toString());
        }
    }

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

    public void searchLocation(String search) {
        System.out.println("search: " + search);
        URI url = UriComponentsBuilder.fromHttpUrl("https://geocoder.cit.api.here.com/6.2/geocode.json")
                .queryParam("searchtext", search)
                .queryParam("app_id", "ZurixIlHeTvNn84GbCkq")
                .queryParam("app_code", "sw87CS8QKIoFiJ4vfa8r3A")
                .queryParam("gen", 2)
                .build().encode().toUri();
        String locationAddress = restTemplate.getForObject(url, String.class);
        System.out.println(locationAddress);


//       String locationAddress = restTemplate.getForObject(url, String.class);
//        System.out.println(locationAddress);
//        Gson gson = new Gson();
//        LocationAddress locationAddress1 = gson.fromJson(locationAddress, LocationAddress.class);
//        System.out.println("ADDRES ADDRES");
//        System.out.println(locationAddress1.getResponse().getView().size());
//       LocationAddress[] locationAddress = restTemplate.getForObject(url, LocationAddress[].class);
//        System.out.println(locationAddress[0].getResponse().getView().get(0).getResult().get(0).getLocation().getAddress().getLabel());
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


}
