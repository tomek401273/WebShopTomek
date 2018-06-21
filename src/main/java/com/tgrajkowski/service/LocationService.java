package com.tgrajkowski.service;

import com.tgrajkowski.model.location.HeaderRequestInterceptor;
import com.tgrajkowski.model.location.LocationAddress;
import com.tgrajkowski.model.location.country.Country;
import com.tgrajkowski.model.location.country.CountryDto;
import com.tgrajkowski.model.location.country.CountryMapper;
import com.tgrajkowski.model.location.response.view.result.location.address.Address;
import com.tgrajkowski.model.location.response.view.result.location.address.AddressDto;
import com.tgrajkowski.model.location.response.view.result.location.address.AddressMapper;
import com.tgrajkowski.model.model.dao.CountryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
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

    public List<Country> getAllCountryFromServer() {
        deleteAllCountry();
        URI uri = UriComponentsBuilder.fromHttpUrl("https://restcountries-v1.p.mashape.com/all")
                .build().encode().toUri();
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        interceptors.add(new HeaderRequestInterceptor("Accept", MediaType.APPLICATION_JSON_VALUE));
        interceptors.add(new HeaderRequestInterceptor("X-Mashape-Key", "us7DCMbPDWmshXfiu9KNu65k5uFBp1fmAZljsnxoPKgQczv9BJ"));
        restTemplate.setInterceptors(interceptors);

        CountryDto[] countries = restTemplate.getForObject(uri, CountryDto[].class);
        List<Country> countryList = new ArrayList<>();
        for (CountryDto countryDto : countries) {
            Country country = countryMapper.mapToCountry(countryDto);
            countryList.add(country);
            countryDao.save(country);
        }
        return countryList;
    }

    public AddressDto searchLocation(String search) {
        URI url = UriComponentsBuilder.fromHttpUrl("https://geocoder.cit.api.here.com/6.2/geocode.json")
                .queryParam("searchtext", search)
                .queryParam("app_id", "ZurixIlHeTvNn84GbCkq")
                .queryParam("app_code", "sw87CS8QKIoFiJ4vfa8r3A")
                .build().encode().toUri();
        LocationAddress locationAddress = restTemplate.getForObject(url, LocationAddress.class);
        Address address = locationAddress.getResponse().getView()[0].getResult()[0].getLocation().getAddress();
        AddressDto addressDto = addressMapper.mapToAddresDto(address);
        String matchesLevel = locationAddress.getResponse().getView()[0].getResult()[0].getMatchLevel();
        if (matchesLevel.equals("street") || matchesLevel.equals("district")) {
            return addressDto;
        }
        return null;
    }

    public boolean deleteAllCountry() {
        countryDao.deleteAll();
        return countryDao.count() == 0;
    }

    public List<CountryDto> getCountriesListFromDatabase() {
        List<Country> countryList = countryDao.findAll();
        return countryMapper.mapToCountryDtoList(countryList);
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
