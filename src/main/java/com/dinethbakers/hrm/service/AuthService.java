package com.dinethbakers.hrm.service;

import com.dinethbakers.hrm.entity.UserEntity;
import com.dinethbakers.hrm.model.login.LoginRequest;

public interface AuthService {
    UserEntity validateLogin(LoginRequest dto);
}
