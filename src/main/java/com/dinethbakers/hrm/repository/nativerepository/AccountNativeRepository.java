package com.dinethbakers.hrm.repository.nativerepository;

import com.dinethbakers.hrm.entity.UserEntity;
import org.springframework.transaction.annotation.Transactional;

public interface AccountNativeRepository {
    @Transactional
    UserEntity editAccount(UserEntity account);
}
