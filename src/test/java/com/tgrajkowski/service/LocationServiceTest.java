package com.tgrajkowski.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tgrajkowski.model.location.HeaderRequestInterceptor;
import com.tgrajkowski.model.location.LocationAddress;
import com.tgrajkowski.model.location.country.Country;
import com.tgrajkowski.model.location.country.CountryDto;
import com.tgrajkowski.model.location.country.CountryMapper;
import com.tgrajkowski.model.location.response.Response;
import com.tgrajkowski.model.location.response.view.View;
import com.tgrajkowski.model.location.response.view.result.Result;
import com.tgrajkowski.model.location.response.view.result.location.Location;
import com.tgrajkowski.model.location.response.view.result.location.address.Address;
import com.tgrajkowski.model.location.response.view.result.location.address.AddressDto;
import com.tgrajkowski.model.location.response.view.result.location.address.AddressMapper;
import com.tgrajkowski.model.model.dao.CountryDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationServiceTest {
    @InjectMocks
    LocationService locationService;

    @Mock
    private CountryMapper countryMapper;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CountryDao countryDao;

    @Mock
    private AddressMapper addressMapper;

    @Test
    public void getAllCountryFromServer() {
        URI uri = UriComponentsBuilder.fromHttpUrl("https://restcountries-v1.p.mashape.com/all")
                .build().encode().toUri();
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        interceptors.add(new HeaderRequestInterceptor("Accept", MediaType.APPLICATION_JSON_VALUE));
        interceptors.add(new HeaderRequestInterceptor("X-Mashape-Key", "us7DCMbPDWmshXfiu9KNu65k5uFBp1fmAZljsnxoPKgQczv9BJ"));
        restTemplate.setInterceptors(interceptors);
        CountryDto[] countryDtos = new CountryDto[1];
        CountryDto countryDto1 = new CountryDto();
        countryDto1.setAlpha2Code("Alpha2Code");
        countryDto1.setAlpha3Code("Alpha3Code");
        countryDto1.setApproved(true);
        countryDto1.setCode("code");
        countryDto1.setName("name");
        countryDtos[0] = countryDto1;
        Country country = new Country();
        country.setAlpha2Code("Alpha2Code");
        country.setAlpha3Code("Alpha3Code");
        country.setApproved(true);
        country.setName("name");
        Mockito.when(restTemplate.getForObject(uri, CountryDto[].class)).thenReturn(countryDtos);
        Mockito.when(countryMapper.mapToCountry(countryDto1)).thenReturn(country);
        // When
        List<Country> retrieved = locationService.getAllCountryFromServer();
        Assert.assertEquals(1, retrieved.size());
        Assert.assertEquals("Alpha2Code", retrieved.get(0).getAlpha2Code());
        Assert.assertEquals("Alpha3Code", retrieved.get(0).getAlpha3Code());
        Assert.assertTrue(retrieved.get(0).isApproved());
        Assert.assertEquals("name", retrieved.get(0).getName());
    }


    @Test
    public void getAllCountryFromServerEmptyListTest() {
        URI uri = UriComponentsBuilder.fromHttpUrl("https://restcountries-v1.p.mashape.com/all")
                .build().encode().toUri();
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        interceptors.add(new HeaderRequestInterceptor("Accept", MediaType.APPLICATION_JSON_VALUE));
        interceptors.add(new HeaderRequestInterceptor("X-Mashape-Key", "us7DCMbPDWmshXfiu9KNu65k5uFBp1fmAZljsnxoPKgQczv9BJ"));
        restTemplate.setInterceptors(interceptors);
        CountryDto[] countryDtos = new CountryDto[0];
        Mockito.when(restTemplate.getForObject(uri, CountryDto[].class)).thenReturn(countryDtos);
        // When
        List<Country> retrieved = locationService.getAllCountryFromServer();
        Assert.assertEquals(0, retrieved.size());
    }

    @Test
    public void searchLocation() throws URISyntaxException {
        URI uri = new URI("https://geocoder.cit.api.here.com/6.2/geocode.json?searchtext=search&app_id=ZurixIlHeTvNn84GbCkq&app_code=sw87CS8QKIoFiJ4vfa8r3A");

        Address address = new Address();
        address.setCity("city");
        address.setCountry("country");
        address.setDistrict("district");
        address.setPostalCode("postalCode");
        address.setStreet("street");
        address.setState("state");
        address.setSubdistrict("subDistrict");
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
        AddressDto addressDto = new AddressDto();
        addressDto.setCity("city");
        addressDto.setCountry("country");
        addressDto.setDistrict("district");
        addressDto.setPostalCode("postalCode");
        addressDto.setStreet("street");
        addressDto.setState("state");
        addressDto.setSubdistrict("subDistrict");
        Mockito.when(addressMapper.mapToAddresDto(address)).thenReturn(addressDto);

        locationAddress.getResponse().getView()[0].getResult()[0].getLocation().setAddress(address);
        locationAddress.getResponse().getView()[0].getResult()[0].setMatchLevel("street");
        Mockito.when(restTemplate.getForObject(uri, LocationAddress.class)).thenReturn(locationAddress);

        //When
        AddressDto retrieved = locationService.searchLocation("search");
        // Then
        Assert.assertEquals("city", retrieved.getCity());
        Assert.assertEquals("country", retrieved.getCountry());
        Assert.assertEquals("district", retrieved.getDistrict());
        Assert.assertEquals("street", retrieved.getStreet());
        Assert.assertEquals("state", retrieved.getState());
        Assert.assertEquals("state", retrieved.getState());
        Assert.assertEquals("subDistrict", retrieved.getSubdistrict());
    }

    @Test
    public void deleteAllCountrySuccess() {
        // Given
        Mockito.when(countryDao.count()).thenReturn((long) 0);
        boolean retrieved = locationService.deleteAllCountry();
        Assert.assertTrue(retrieved);
    }

    @Test
    public void deleteAllCountryFail() {
        // Given
        Mockito.when(countryDao.count()).thenReturn((long) 1);
        boolean retrieved = locationService.deleteAllCountry();
        Assert.assertFalse(retrieved);
    }

    @Test
    public void getCountriesListFromDatabase() {
        List<Country> countryList = new ArrayList<>();
        Country country1 = new Country();
        country1.setAlpha2Code("AlphaCode2");
        country1.setAlpha3Code("AlphaCode3");
        country1.setApproved(true);
        country1.setName("name");
        country1.setId((long) 1);
        countryList.add(country1);
        Mockito.when(countryDao.findAll()).thenReturn(countryList);
        List<CountryDto> countryDtoList = new ArrayList<>();
        CountryDto countryDto = new CountryDto();
        countryDto.setName("name");
        countryDto.setCode("AlphaCode2");
        countryDto.setApproved(true);
        countryDtoList.add(countryDto);
        Mockito.when(countryMapper.mapToCountryDtoList(countryList)).thenReturn(countryDtoList);
        // When
        List<CountryDto> retrieved = locationService.getCountriesListFromDatabase();
        Assert.assertEquals(1, retrieved.size());
        Assert.assertEquals("name", retrieved.get(0).getName());
        Assert.assertEquals("AlphaCode2", retrieved.get(0).getCode());
        Assert.assertTrue(retrieved.get(0).isApproved());
    }

    @Test
    public void getCountriesListFromDatabaseNotApproved() {
        List<Country> countryList = new ArrayList<>();
        Country country1 = new Country();
        country1.setAlpha2Code("AlphaCode2");
        country1.setAlpha3Code("AlphaCode3");
        country1.setApproved(false);
        country1.setName("name");
        country1.setId((long) 1);
        countryList.add(country1);
        Mockito.when(countryDao.findAll()).thenReturn(countryList);
        List<CountryDto> countryDtoList = new ArrayList<>();
        CountryDto countryDto = new CountryDto();
        countryDto.setName("name");
        countryDto.setCode("AlphaCode2");
        countryDto.setApproved(false);
        countryDtoList.add(countryDto);
        Mockito.when(countryMapper.mapToCountryDtoList(countryList)).thenReturn(countryDtoList);
        // When
        List<CountryDto> retrieved = locationService.getCountriesListFromDatabase();
        Assert.assertEquals(1, retrieved.size());
        Assert.assertEquals("name", retrieved.get(0).getName());
        Assert.assertEquals("AlphaCode2", retrieved.get(0).getCode());
        Assert.assertFalse(retrieved.get(0).isApproved());
    }

    @Test
    public void getApprovedCountry() {
        // Given
        Mockito.when(countryDao.findByApproved(true)).thenReturn(createListCountries());
        // When
        List<String> retrieved = locationService.getApprovedCountry();
        // Then
        Assert.assertEquals(3, retrieved.size());
        Assert.assertEquals("AlphaCode2", retrieved.get(0));
        Assert.assertEquals("AlphaCode22", retrieved.get(1));
        Assert.assertEquals("AlphaCode23", retrieved.get(2));

    }

    @Test
    public void getApprovedCountryTestEmptyList() {
        // Given
        Mockito.when(countryDao.findByApproved(true)).thenReturn(new ArrayList<>());
        // When
        List<String> retrieved = locationService.getApprovedCountry();
        // Then
        Assert.assertEquals(0, retrieved.size());
    }

    public List<Country> createListCountries() {
        List<Country> countryList = new ArrayList<>();
        Country country1 = new Country();
        country1.setAlpha2Code("AlphaCode2");
        country1.setAlpha3Code("AlphaCode3");
        country1.setApproved(true);
        country1.setName("name");
        country1.setId((long) 1);
        countryList.add(country1);

        Country country2 = new Country();
        country2.setAlpha2Code("AlphaCode22");
        country2.setAlpha3Code("AlphaCode32");
        country2.setApproved(true);
        country2.setName("name2");
        country2.setId((long) 2);
        countryList.add(country2);

        Country country3 = new Country();
        country3.setAlpha2Code("AlphaCode23");
        country3.setAlpha3Code("AlphaCode33");
        country3.setApproved(true);
        country3.setName("name3");
        country3.setId((long) 3);
        countryList.add(country3);
        return countryList;
    }
}