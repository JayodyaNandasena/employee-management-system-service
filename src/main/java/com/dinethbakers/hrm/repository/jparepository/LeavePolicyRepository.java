package com.dinethbakers.hrm.repository.jparepository;

import com.dinethbakers.hrm.entity.LeavePolicyEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface LeavePolicyRepository extends CrudRepository<LeavePolicyEntity,String> {
    Optional<LeavePolicyEntity> findByNoOfPTODays(Integer noOfPTODays);
}
