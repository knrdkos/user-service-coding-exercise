package com.example.userapp.domain.user.controller;

import com.example.userapp.domain.user.dto.UserDto;
import com.example.userapp.domain.user.dto.UserLoginDto;
import com.example.userapp.domain.user.dto.UserLoginResponseDto;
import com.example.userapp.domain.user.dto.UserRegistrationDto;
import com.example.userapp.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class AuthUserController {
    private final UserService userService;

    @PostMapping("/register")
    public UserDto registration(@RequestBody @Valid UserRegistrationDto userRegistrationDto) {
        return userService.registration(userRegistrationDto);
    }

    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginDto userLoginDto) {
        return userService.login(userLoginDto);
    }

}
