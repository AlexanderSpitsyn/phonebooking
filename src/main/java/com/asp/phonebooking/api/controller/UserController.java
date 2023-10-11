package com.asp.phonebooking.api.controller;

import com.asp.phonebooking.api.dto.CreateUserDTO;
import com.asp.phonebooking.api.dto.ReadUserDTO;
import com.asp.phonebooking.api.dto.UpdateUserDTO;
import com.asp.phonebooking.api.mapper.UserMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserController(UserDetailsManager userDetailsManager,
                          PasswordEncoder passwordEncoder,
                          UserMapper userMapper) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ReadUserDTO create(@RequestBody @Valid CreateUserDTO user) {
        if (userDetailsManager.userExists(user.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A user with the same name already exists");
        }

        var userDetails = buildUserDetails(user.getUsername(), user);
        userDetailsManager.createUser(userDetails);
        return userMapper.toReadUserDto(userDetails);
    }

    @PutMapping(path = "/{username}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ReadUserDTO update(@PathVariable String username, @RequestBody @Valid UpdateUserDTO user) {
        if (!userDetailsManager.userExists(username)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The user doesn't exist");
        }

        var userDetails = buildUserDetails(username, user);
        userDetailsManager.updateUser(userDetails);
        return userMapper.toReadUserDto(userDetails);
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable String username) {
        if (!userDetailsManager.userExists(username)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The user doesn't exist");
        }

        userDetailsManager.deleteUser(username);
    }

    private UserDetails buildUserDetails(String username, UpdateUserDTO user) {
        var authorities = user.getRoles().stream()
                .map(Enum::name)
                .toArray(String[]::new);
        return User.builder()
                .passwordEncoder(passwordEncoder::encode)
                .username(username)
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}
