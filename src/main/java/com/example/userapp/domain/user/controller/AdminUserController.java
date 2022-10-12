package com.example.userapp.domain.user.controller;

import com.example.userapp.domain.user.dto.UserDto;
import com.example.userapp.domain.user.dto.UserUpdateDto;
import com.example.userapp.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO : add roles check
@RequestMapping("/v1/admin/users")
@RestController
@RequiredArgsConstructor
public class AdminUserController {
    private final UserService userService;

    // TODO : pagination, findAllBy*, maybe a wrapper object
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAll();
    }

    // TODO : depending on business scenarios we can add POST method for new user \
    //  or we can force it to be registration process always ( verification email etc )
    //  batching new user creation might be useful
    //    @PostMapping
    //    public UserDto createNewUser(UserRegistrationDto dto) {
    //        return userService.registration(dto);
    //    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(String.format("{\"message\" : \"user %d has been deleted\"}", id));
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserUpdateDto userUpdateDto) {
        return userService.updateUser(id, userUpdateDto);
    }
}
