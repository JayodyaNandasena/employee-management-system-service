package com.dinethbakers.hrm.repository.jparepository;

import com.dinethbakers.hrm.entity.RoleEntity;
import com.dinethbakers.hrm.util.RoleEnum;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<RoleEntity,String> {
    Optional<RoleEntity> findByName(RoleEnum name);
}
