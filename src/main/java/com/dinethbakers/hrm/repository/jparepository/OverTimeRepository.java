package com.dinethbakers.hrm.repository.jparepository;

import com.dinethbakers.hrm.entity.OverTimeEntity;
import com.dinethbakers.hrm.util.StatusEnum;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface OverTimeRepository extends CrudRepository<OverTimeEntity,String> {
    @Query("SELECT max(o.requestId) FROM OverTimeEntity o")
    String findMaxOverTimeRequestId();

    List<OverTimeEntity> findByStatus(StatusEnum statusEnum);

    @Query("SELECT SUM(o.paymentAmount) FROM OverTimeEntity o WHERE o.employee.id = :employeeId AND o.status = 'APPROVED'")
    BigDecimal findTotalOvertimePaymentByEmployeeId(@Param("employeeId") String employeeId);

    @Query("SELECT COALESCE(SUM(o.paymentAmount), 0) " +
            "FROM OverTimeEntity o " +
            "WHERE o.employee.id = :employeeId " +
            "AND o.status = 'APPROVED' " +
            "AND o.date BETWEEN :startOfMonth AND :endOfMonth")
    BigDecimal findTotalOvertimePaymentByEmployeeIdThisMonth(
            @Param("employeeId") String employeeId,
            @Param("startOfMonth") LocalDate startOfMonth,
            @Param("endOfMonth") LocalDate endOfMonth);

}
