package com.tgrajkowski.model.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public UserDto mapToUserDto(Users user) {
        return new UserDto(
                user.getName(),
                user.getSurname(),
                user.getUserAddress().getCountry(),
                user.getUserAddress().getCity(),
                user.getUserAddress().getPostCode(),
                user.getUserAddress().getStreet());
    }

    public Users mapToUser(UserDto userDto) {
        return new Users(userDto.getName(), userDto.getSurname(), userDto.getPassword(), userDto.getLogin());
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
        List<GrantedAuthority> userRoleList = new ArrayList<>();
        System.out.println("getiing roleList");
        List<Role> roleList = user.getRoleList();
        System.out.println("roleList size: "+roleList.size());
        for (Role role : roleList) {
            System.out.println("role: " + role.getId()+" code: "+ role.getCode());
        }
        System.out.println("end roleList");
        userRoleList = user.getRoleList().stream()
                .map(role -> new SimpleGrantedAuthority(role.getCode()))
                .collect(Collectors.toList());
        System.out.println("UserMapper roleList: "+userRoleList.toString());

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), userRoleList);
        return userDetails;
    }
}
