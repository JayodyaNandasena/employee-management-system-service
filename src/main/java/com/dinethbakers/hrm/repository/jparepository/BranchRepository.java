package com.dinethbakers.hrm.repository.jparepository;

import com.dinethbakers.hrm.entity.BranchEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BranchRepository extends CrudRepository<BranchEntity,Integer> {
    @Query("SELECT b.name FROM BranchEntity b")
    List<String> findAllNames();
    Optional<BranchEntity> findByName(String name);
}
