package com.dinethbakers.hrm.repository.jparepository;

import com.dinethbakers.hrm.entity.JobRoleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface JobRoleRepository extends CrudRepository<JobRoleEntity,Integer> {
    @Query("SELECT j.title FROM JobRoleEntity j")
    List<String> findAllTitles();
    Optional<JobRoleEntity> findByTitle(String title);
}
