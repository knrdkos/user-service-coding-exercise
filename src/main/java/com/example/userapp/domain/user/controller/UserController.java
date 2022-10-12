package com.example.userapp.domain.user.controller;

import com.example.userapp.domain.user.dto.UserDto;
import com.example.userapp.domain.user.dto.UserUpdateDto;
import com.example.userapp.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public UserDto currentUser(@AuthenticationPrincipal UserDto authUser) {
        return userService.getCurrentUser(authUser);
    }

    @PutMapping
    public UserDto updateUser(@AuthenticationPrincipal UserDto authUser, @RequestBody UserUpdateDto userUpdateDto) {
        return userService.updateUser(authUser.getId(), userUpdateDto);
    }
}
