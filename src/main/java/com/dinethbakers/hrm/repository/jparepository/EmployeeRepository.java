package com.dinethbakers.hrm.repository.jparepository;

import com.dinethbakers.hrm.entity.BranchEntity;
import com.dinethbakers.hrm.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends CrudRepository<EmployeeEntity,String> {
    @Query("SELECT max(e.employeeId) FROM EmployeeEntity e")
    String findMaxEmployeeId();

    @Query("SELECT e.branch FROM EmployeeEntity e WHERE e.employeeId = :employeeId")
    BranchEntity findBranchByEmployeeId(@Param("employeeId") String employeeId);
}
