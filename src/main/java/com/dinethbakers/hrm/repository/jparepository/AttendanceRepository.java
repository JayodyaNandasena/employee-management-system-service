package com.dinethbakers.hrm.repository.jparepository;

import com.dinethbakers.hrm.entity.AttendanceEntity;
import com.dinethbakers.hrm.entity.EmployeeEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AttendanceRepository extends CrudRepository<AttendanceEntity,Integer> {
    List<AttendanceEntity> findByEmployeeOrderByDateDesc(EmployeeEntity employee);
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM AttendanceEntity a WHERE a.employee.employeeId = :employeeId AND a.timeIn IS NOT NULL")
    boolean existsByEmployeeIdAndTimeInIsNotNull(@Param("employeeId") String employeeId);

    @Query("SELECT COUNT(a) > 0 FROM AttendanceEntity a WHERE a.employee.employeeId = :employeeId AND a.timeIn IS NOT NULL AND a.date = :date")
    boolean existsByEmployeeIdAndTimeInAndDate(@Param("employeeId") String employeeId, @Param("date") LocalDate date);

    @Query("SELECT a.attendanceId FROM AttendanceEntity a WHERE a.employee.employeeId = :employeeId AND a.date = :date")
    Integer findIdByEmployeeIdAndDate(@Param("employeeId") String employeeId, @Param("date") LocalDate date);

    @Modifying
    @Transactional
    @Query("UPDATE AttendanceEntity a SET a.timeOut = :time WHERE a.attendanceId = :attendanceId")
    void updateTimeOutById(@Param("attendanceId") Integer attendanceId, @Param("time") LocalTime time);
}
