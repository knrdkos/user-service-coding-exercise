package com.example.userapp.domain.user.service;

import com.example.userapp.configuration.security.JwtUtils;
import com.example.userapp.domain.user.dto.*;
import com.example.userapp.domain.user.entity.UserEntity;
import com.example.userapp.domain.user.repository.UserRepository;
import com.example.userapp.exception.AppException;
import com.example.userapp.exception.ExceptionReason;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.bytebuddy.utility.RandomString;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

// TODO : export interface, mockito can extend it for testing but it's cleaner as an interface
@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Transactional
    public UserDto registration(UserRegistrationDto userRegistrationDto) {
        userRepository
                .findByEmail(userRegistrationDto.getEmail())
                .ifPresent(u -> {
                    throw new AppException(ExceptionReason.USER_ALREADY_EXISTS);
                });

        UserEntity userEntity = UserEntity.builder()
                .email(userRegistrationDto.getEmail())
                .name(userRegistrationDto.getName())
                .password(passwordEncoder.encode(userRegistrationDto.getPassword()))
                .verificationCode(RandomString.make(64)) // TODO : separate table for email verifications joined on user id
                .enabled(true)
                .emailVerified(true) // TODO : send email for enabled
                .build();
        log.debug("Creating new user with email {}", userRegistrationDto.getEmail());
        userRepository.save(userEntity);
        return convertToDto(userEntity);
    }

    @Transactional(readOnly = true)
    public UserLoginResponseDto login(UserLoginDto userLoginDto) {
        UserEntity userEntity = userRepository
                .findByEmail(userLoginDto.getEmail()).orElseThrow(() -> new AppException(ExceptionReason.USER_UNAUTHORIZED));
        String dbUserPassword = userEntity.getPassword();

        if (passwordEncoder.matches(userLoginDto.getPassword(), dbUserPassword)) {
            log.trace("Logged in user {}", userLoginDto.getEmail());
            return new UserLoginResponseDto(userEntity.getEmail(), jwtUtils.encode(userEntity.getEmail()), userEntity.getId());
        } else {
            log.trace("Error logging user {}", userLoginDto.getEmail());
            throw new AppException(ExceptionReason.USER_UNAUTHORIZED);
        }
    }

    @Transactional
    public UserDto updateUser(Long id, UserUpdateDto userUpdateDto) {
        UserEntity userEntity = userRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ExceptionReason.USER_DOESNT_EXISTS));

        if (userUpdateDto.getEmail() != null) {
            userEntity.setEmail(userUpdateDto.getEmail());
            // TODO : send verification email for new address
        }
        if (userUpdateDto.getName() != null) {
            userEntity.setName(userUpdateDto.getName());
        }

        return convertToDto(userEntity);
    }

    public UserDto getCurrentUser(UserDto authUser) {
        UserEntity userEntity = userRepository
                .findById(authUser.getId()).orElseThrow(() -> new AppException(ExceptionReason.USER_DOESNT_EXISTS));
        return convertToDto(userEntity);
    }

    public void deleteUser(Long id) {
        log.debug("Deleting user with id {}", id);
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new AppException(ExceptionReason.USER_DOESNT_EXISTS);
        }
    }

    public UserDto findUserById(Long id) {
        return convertToDto(userRepository
                .findById(id)
                .orElseThrow(() -> new AppException(ExceptionReason.USER_DOESNT_EXISTS))
        );
    }

    public List<UserDto> findAll() {
        return userRepository
                .findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }


    private UserDto convertToDto(UserEntity userEntity) {
        return UserDto.builder()
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .id(userEntity.getId())
                .build();
    }
}
