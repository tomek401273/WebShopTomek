package com.tgrajkowski.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserMapper {
    public UserDto mapToUserDto(User user) {
        return new UserDto(user.getName(), user.getSurname(), user.getPassword(), user.getLogin());
    }

    public User mapToUser(UserDto userDto) {
        return new User(userDto.getName(), userDto.getSurname(), userDto.getPassword(), userDto.getLogin());
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
    public UserDetails mapToUserDetails(User user) {
        List<GrantedAuthority> emptyList=new ArrayList<>();
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(),emptyList);
        return userDetails;
    }
}
