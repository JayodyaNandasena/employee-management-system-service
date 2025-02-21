package com.dinethbakers.hrm.service.impl;

import com.dinethbakers.hrm.entity.UserEntity;
import com.dinethbakers.hrm.model.LoginRequest;
import com.dinethbakers.hrm.repository.jparepository.UserRepository;
import com.dinethbakers.hrm.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final ObjectMapper mapper;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserEntity validateLogin(LoginRequest dto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getUsername(),
                            dto.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            log.error("Authentication failed: " + e.getMessage());
            throw e;
        }

        return userRepository.findByUsername(dto.getUsername())
                .orElseThrow();
    }
}
