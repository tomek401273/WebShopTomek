package com.tgrajkowski.model.location.country;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CountryMapper {
    public Country mapToCountry(CountryDto countryDto) {
        return new Country(countryDto.getName(), countryDto.getCode());
    }

    public List<Country> mapToCountryDtoList(CountryDto[] countryDtos) {
        List<Country> countryList = new ArrayList<>();
        for (CountryDto countryDto: countryDtos) {
            countryList.add(mapToCountry(countryDto));
        }
        return  countryList;
    }

    public CountryDto mapToCountryDto(Country country) {
        return new CountryDto(country.getName(), country.getAlpha2Code());
    }

    public List<CountryDto> mapToCountryDtoList(List<Country> countryList) {
        List<CountryDto> countryDtoList = new ArrayList<>();
        for(Country country: countryList) {
            countryDtoList.add(mapToCountryDto(country));
        }
        return countryDtoList;
    }

}
