package com.tgrajkowski.model.location.country;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CountryMapper {
    public Country mapToCountry(CountryDto countryDto) {
        return new Country(countryDto.getName(), countryDto.getAlpha2Code(), countryDto.getAlpha3Code(), false);
    }

    public CountryDto mapToCountryDto(Country country) {
        return new CountryDto(country.getName(), country.getAlpha2Code(), country.isApproved());
    }

    public List<CountryDto> mapToCountryDtoList(List<Country> countryList) {
        List<CountryDto> countryDtoList = new ArrayList<>();
        for(Country country: countryList) {
            countryDtoList.add(mapToCountryDto(country));
        }
        return countryDtoList;
    }

}
