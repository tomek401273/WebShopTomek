package com.tgrajkowski.model.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgrajkowski.model.location.response.view.result.location.address.AddressDto;
import com.tgrajkowski.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private LocationService locationService;

    public UserDto mapToUserDto(Users user) {
        UserDto userDto = new UserDto(
                user.getName(),
                user.getSurname(),
                user.getUserAddress().getCountry(),
                user.getUserAddress().getCity(),
                user.getUserAddress().getPostCode(),
                user.getUserAddress().getStreet());
        userDto.setDistrict(user.getUserAddress().getDistrict());
        userDto.setHouse(user.getUserAddress().getHouse());
        userDto.setApartment(user.getUserAddress().getApartment());

        return userDto;
    }

    public Users mapToUserPasswordEncode(UserDto userDto) {
        Users users = new Users();
        String passwordEncoded = bCryptPasswordEncoder.encode(userDto.getPassword());
        users.setPassword(passwordEncoded);
        users.setLogin(userDto.getLogin());
        users.setName(userDto.getName());
        users.setSurname(userDto.getSurname());

        AddressDto addressDto = locationService.searchLocation(userDto.getAddress());
        if (addressDto != null) {
            UserAddress userAddress = new UserAddress();
            userAddress.setCountry(addressDto.getCountry());
            userAddress.setState(addressDto.getState());
            userAddress.setCounty(addressDto.getCounty());
            userAddress.setCity(addressDto.getCity());
            userAddress.setDistrict(addressDto.getDistrict());
            userAddress.setSubdistrict(addressDto.getSubdistrict());
            userAddress.setStreet(addressDto.getStreet());
            userAddress.setPostCode(addressDto.getPostalCode());
            userAddress.setHouse(userDto.getHouse());
            userAddress.setApartment(userDto.getApartment());
            users.setUserAddress(userAddress);
            return users;
        }
        return null;
    }


    public Users mapToUserWithoutPassword(UserDto userDto, Users users) {
        users.setLogin(userDto.getLogin());
        users.setName(userDto.getName());
        users.setSurname(userDto.getSurname());

        AddressDto addressDto = locationService.searchLocation(userDto.getAddress());
        if (addressDto != null) {
            UserAddress userAddress = users.getUserAddress();
            userAddress.setCountry(addressDto.getCountry());
            userAddress.setState(addressDto.getState());
            userAddress.setCounty(addressDto.getCounty());
            userAddress.setCity(addressDto.getCity());
            userAddress.setDistrict(addressDto.getDistrict());
            userAddress.setSubdistrict(addressDto.getSubdistrict());
            userAddress.setStreet(addressDto.getStreet());
            userAddress.setPostCode(addressDto.getPostalCode());
            userAddress.setHouse(userDto.getHouse());
            userAddress.setApartment(userDto.getApartment());
            users.setUserAddress(userAddress);
            return users;
        }
        return null;
    }



    public UserDto mapToUserDto(InputStream inputStream) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(inputStream, UserDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public UserDetails mapToUserDetails(Users user) {
        List<GrantedAuthority> userRoleList = user.getRoleList().stream()
                .map(role -> new SimpleGrantedAuthority(role.getCode()))
                .collect(Collectors.toList());

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), userRoleList);
        return userDetails;
    }
}
