package com.dinethbakers.hrm.repository.nativerepository.impl;

import com.dinethbakers.hrm.entity.EmployeeEntity;
import com.dinethbakers.hrm.repository.jparepository.EmployeeRepository;
import com.dinethbakers.hrm.repository.nativerepository.EmployeeNativeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class EmployeeNativeRepositoryImpl implements EmployeeNativeRepository {
    private final JdbcTemplate jdbcTemplate;
    private final EmployeeRepository jpaRepository;
    @Override
    public EmployeeEntity editEmployee(EmployeeEntity employee) {
        String sql = "UPDATE employee SET first_name = ?, " +
                "last_name = ?, nic = ?, dob = ?, profile_picture = ?, " +
                "hired_date = ?, address = ?, email = ?, gender = ?, " +
                "branch_id = ?, job_role_id = ? WHERE employee_id = ?";
        int rowsAffected = jdbcTemplate.update(sql,
                employee.getFirstName(),
                employee.getLastName(),
                employee.getNic(),
                employee.getDob(),
                employee.getProfilePicture(),
                employee.getHiredDate(),
                employee.getAddress(),
                employee.getEmail(),
                employee.getGender().name(),
                //employee.getBranch().getBranchId(),
                //employee.getJobRole().getJobRoleId(),
                employee.getEmployeeId());

        if (rowsAffected > 0) {
            return jpaRepository.findById(employee.getEmployeeId()).get();
        } else {
            return null;
        }
    }
}
