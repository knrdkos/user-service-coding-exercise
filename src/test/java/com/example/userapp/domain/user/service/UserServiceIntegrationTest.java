package com.example.userapp.domain.user.service;

import com.example.userapp.configuration.security.JwtUtils;
import com.example.userapp.domain.user.dto.UserDto;
import com.example.userapp.domain.user.dto.UserRegistrationDto;
import com.example.userapp.domain.user.entity.UserEntity;
import com.example.userapp.domain.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceIntegrationTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @LocalServerPort
    private Integer port;


    @Test
    public void deleteUser() {
        //setup
        String email = "knrd.kostrzewa@gmail.com";
        UserDto user = userService.registration(UserRegistrationDto.builder()
                .name("Konrad Kostrzewa")
                .email(email)
                .password("qwerty12")
                .build()
        );
        String jwt = jwtUtils.encode(email);
        Optional<UserEntity> byId = userRepository.findById(user.getId());
        assertTrue(byId.isPresent());

        //when
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", jwt);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        String deleteUrl = "http://localhost:" + port + "/v1/admin/users/" + user.getId();
        ResponseEntity<String> result = restTemplate.exchange(deleteUrl, HttpMethod.DELETE, entity, String.class);

        //then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(String.format("{\"message\" : \"user %d has been deleted\"}", user.getId()), result.getBody());
        byId = userRepository.findById(user.getId());
        assertTrue(byId.isEmpty());
    }
}
