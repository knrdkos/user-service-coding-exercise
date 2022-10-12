package com.example.userapp.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@Builder
public class UserRegistrationDto {
    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8, max = 32)
    private String password;
}
