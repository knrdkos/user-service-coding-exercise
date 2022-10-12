package com.example.userapp.configuration.security;

import com.example.userapp.domain.user.dto.UserDto;
import com.example.userapp.domain.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationProvider {

    private final UserDetailsService userDetailsService;

    public Authentication getAuthentication(String username) {
        UserDetails userDetail = userDetailsService.loadUserByUsername(username);
        if (userDetail == null) return null;
        UserEntity userEntity = (UserEntity) userDetail;
        UserDto authenticatedUser = UserDto.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .build();
        return new UsernamePasswordAuthenticationToken(authenticatedUser, "", userDetail.getAuthorities());
    }
}
