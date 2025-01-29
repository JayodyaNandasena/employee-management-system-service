package com.dinethbakers.hrm.repository.jparepository;

import com.dinethbakers.hrm.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity,String> {
    UserEntity findByEmployeeEmployeeId(String employeeId);
    Optional<UserEntity> findByUsername(String username);
}
