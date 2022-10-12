package com.example.userapp.domain.user.controller;

import com.example.userapp.configuration.security.JWTAuthFilter;
import com.example.userapp.domain.user.dto.UserDto;
import com.example.userapp.domain.user.dto.UserRegistrationDto;
import com.example.userapp.domain.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthUserController.class) // example of testing controller layer
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    UserService userService;
    @MockBean
    JWTAuthFilter jwtAuthFilter;

    @MethodSource("userRegistration")
    @ParameterizedTest
    public void testRegistration(UserRegistrationDto dto) throws Exception {
        UserDto result = UserDto.builder().id(1L).email(dto.getEmail()).name(dto.getName()).build();
        when(userService.registration(any(UserRegistrationDto.class))).thenReturn(result);

        mockMvc.perform(post("/v1/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", Matchers.is(dto.getEmail())))
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.name", Matchers.is(dto.getName())));

    }

    public static Stream<Arguments> userRegistration() {
        return Stream.of(
                Arguments.of(UserRegistrationDto.builder().email("test1@test.com").name("name1").password("password1").build()),
                Arguments.of(UserRegistrationDto.builder().email("test2@test.com").name("name2").password("password2").build()),
                Arguments.of(UserRegistrationDto.builder().email("test3@test.com").name("name3").password("password3").build())
        );
    }
}