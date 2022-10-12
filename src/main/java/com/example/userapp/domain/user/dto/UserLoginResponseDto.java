package com.example.userapp.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserLoginResponseDto {
    private String email;
    private String jwt;
    private long id;
}
