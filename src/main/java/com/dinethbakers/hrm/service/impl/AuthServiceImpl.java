package com.dinethbakers.hrm.service.impl;

import com.dinethbakers.hrm.entity.UserEntity;
import com.dinethbakers.hrm.model.LoginRequest;
import com.dinethbakers.hrm.repository.jparepository.UserRepository;
import com.dinethbakers.hrm.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final ObjectMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
//    @Override
//    public ResponseEntity<Map<String, Object>> validateLogin(LoginRequest dto) {
//        Optional<UserEntity> byId = userRepository.findById(dto.getUsername());
//
//        Map<String, Object> result = new HashMap<>();
//        String key = "status";
//
//        if (byId.isPresent()) {
//            UserEntity userEntity = byId.get();
//
//            if(Boolean.TRUE.equals(checkPassword(dto.getPassword(), userEntity.getPassword()))){
//                result.put(key, true);
//                result.put("employeeDetails", mapper.convertValue(userEntity, AccountRead.class));
//                return new ResponseEntity<>(result, HttpStatus.OK);
//            }
//            result.put(key, false);
//            result.put("message", "Invalid password");
//            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
//        }
//        result.put(key, false);
//        result.put("message", "User not found");
//        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
//    }

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
            System.out.println("Authentication failed: " + e.getMessage());
            throw e;
        }

        return userRepository.findByUsername(dto.getUsername())
                .orElseThrow();
    }
}
