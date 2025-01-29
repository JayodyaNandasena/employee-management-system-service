package com.dinethbakers.hrm.repository.jparepository;

import com.dinethbakers.hrm.entity.TimeOffEntity;
import com.dinethbakers.hrm.util.StatusEnum;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TimeOffRepository extends CrudRepository<TimeOffEntity,String> {
    @Query("SELECT max(t.requestId) FROM TimeOffEntity t")
    String findMaxTimeOffRequestId();

    List<TimeOffEntity> findByStatus(StatusEnum statusEnum);
}
