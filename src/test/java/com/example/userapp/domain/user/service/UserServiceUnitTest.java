package com.example.userapp.domain.user.service;

import com.example.userapp.configuration.security.JwtUtils;
import com.example.userapp.domain.user.dto.UserRegistrationDto;
import com.example.userapp.domain.user.entity.UserEntity;
import com.example.userapp.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// unit testing, fastest no need to bring up spring ctx,
//
class UserServiceUnitTest {

    @Test
    public void testRegistration() {
        UserRepository userRepository = mock(UserRepository.class);
        JwtUtils jwtUtils = new JwtUtils("SymmetricKeyJwtExample000000000000", 43200);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        UserService userService = new UserService(userRepository, passwordEncoder, jwtUtils);
        ArgumentCaptor<UserEntity> ag = ArgumentCaptor.forClass(UserEntity.class);
        when(userRepository.findByEmail(eq("test@test.com"))).thenReturn(Optional.empty());

        UserRegistrationDto userRegistrationDto = UserRegistrationDto.builder()
                .email("test@test.com")
                .name("test")
                .password("testpassword")
                .build();
        userService.registration(userRegistrationDto);


        verify(userRepository, times(1)).save(ag.capture());
        UserEntity capturedUserEntity = ag.getValue();

        assertEquals(userRegistrationDto.getEmail(), capturedUserEntity.getEmail());
        assertEquals(userRegistrationDto.getName(), capturedUserEntity.getName());
        assertTrue(passwordEncoder.matches(userRegistrationDto.getPassword(), capturedUserEntity.getPassword()));
        assertNotNull(capturedUserEntity.getVerificationCode());
    }

}