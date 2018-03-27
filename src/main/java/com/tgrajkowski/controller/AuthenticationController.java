package com.tgrajkowski.controller;

import com.tgrajkowski.model.user.Role;
import com.tgrajkowski.model.user.User;
import com.tgrajkowski.model.user.UserDto;
import com.tgrajkowski.model.user.UserMapper;
import com.tgrajkowski.model.model.dao.RoleDao;
import com.tgrajkowski.model.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private UserMapper userMapper = new UserMapper();

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    //    @RequestMapping("/auth")
//    public AuthResponse authic(@RequestBody AuthPar) {
//        final Authentication authentication = authenticationManager.authenticate();
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        return securityContextService.currentUser().map(u -> { final String token = tokenHandler.createTokenForUser(u);
//        return new AuthResponse(token);
//        }).orElseThrow(RuntimeException::new);
//        // it does not happen.
//    }
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public UserDto singUp(@RequestBody @Valid UserDto userDto) {
        String passwordEncoded = bCryptPasswordEncoder.encode(userDto.getPassword());
        userDto.setPassword(passwordEncoded);
        User user = userMapper.mapToUser(userDto);

        List<Role> roles = new ArrayList<>();
        Role role = roleDao.findByName("user");
        roles.add(role);
        user.setRoleList(roles);

        userDao.save(user);
        userDto.setId(user.getId());
        return userDto;
    }

    @RequestMapping(value = "/checkLoginAvailable", method = RequestMethod.POST)
    public boolean checkLoginAvailable(@RequestBody String login) {
        System.out.println(login);
        if (userDao.findByLogin(login) == null) {
            return true;
        }
        return false;
    }

}
