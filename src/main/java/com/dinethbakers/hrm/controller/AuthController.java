package com.dinethbakers.hrm.controller;

import com.dinethbakers.hrm.entity.UserEntity;
import com.dinethbakers.hrm.model.LoginRequest;
import com.dinethbakers.hrm.model.LoginResponse;
import com.dinethbakers.hrm.service.AuthService;
import com.dinethbakers.hrm.service.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@Valid @RequestBody LoginRequest loginUserDto,  HttpServletResponse response) {
        UserEntity authenticatedUser = authService.validateLogin(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);
        long expirationTime = jwtService.getExpirationTime();

        LoginResponse loginResponse = new LoginResponse()
                .setToken(jwtToken)
                .setExpiresIn(expirationTime);

        // set Httponly cookie
        Cookie cookie = new Cookie("token", jwtToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge((int) expirationTime);
        cookie.setDomain("localhost");
        cookie.setAttribute("SameSite","Strict");

        response.addCookie(cookie);

        return ResponseEntity.ok(loginResponse);
    }
}
