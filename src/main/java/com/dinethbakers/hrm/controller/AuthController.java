package com.dinethbakers.hrm.controller;

import com.dinethbakers.hrm.entity.UserEntity;
import com.dinethbakers.hrm.model.LoginRequest;
import com.dinethbakers.hrm.model.LoginResponse;
import com.dinethbakers.hrm.service.AuthService;
import com.dinethbakers.hrm.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest loginUserDto) {
        UserEntity authenticatedUser = authService.validateLogin(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}
