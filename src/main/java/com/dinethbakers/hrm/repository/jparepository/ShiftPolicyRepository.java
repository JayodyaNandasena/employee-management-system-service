package com.dinethbakers.hrm.repository.jparepository;

import com.dinethbakers.hrm.entity.ShiftPolicyEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.Optional;

public interface ShiftPolicyRepository extends CrudRepository<ShiftPolicyEntity,String> {
    Optional<ShiftPolicyEntity> findByStartTimeAndEndTime(
            LocalTime startTime, LocalTime endTime);

    @Query("SELECT s FROM ShiftPolicyEntity s WHERE s.totalHours = :totalHours " +
            "AND s.startTime IS NULL " +
            "AND s.endTime IS NULL")
    Optional<ShiftPolicyEntity> findByTotalHours(@Param("totalHours") LocalTime totalHours);
}
