package com.dinethbakers.hrm.service.impl;

import com.dinethbakers.hrm.entity.EmployeeEntity;
import com.dinethbakers.hrm.entity.MessageEntity;
import com.dinethbakers.hrm.entity.OverTimeEntity;
import com.dinethbakers.hrm.model.*;
import com.dinethbakers.hrm.repository.jparepository.BranchRepository;
import com.dinethbakers.hrm.repository.jparepository.EmployeeRepository;
import com.dinethbakers.hrm.repository.jparepository.MessageRepository;
import com.dinethbakers.hrm.repository.jparepository.OverTimeRepository;
import com.dinethbakers.hrm.service.OverTimeService;
import com.dinethbakers.hrm.util.StatusEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OverTimeServiceImpl implements OverTimeService {
    private final OverTimeRepository overTimeRepository;
    private final EmployeeRepository employeeRepository;
    private final BranchRepository branchRepository;
    private final MessageRepository messageRepository;
    private final ObjectMapper mapper;

    @Override
    public ResponseEntity<Map<String, Object>> persistRequest(OverTimeRequest dto) {
        Map<String, Object> result = new HashMap<>();
        String key1 = "status";
        String key2 = "message";


        Optional<EmployeeEntity> employeeById = employeeRepository.findById(dto.getEmployeeId());

        if (employeeById.isEmpty()){
            result.put(key1, false);
            result.put(key2, "Employee not found.");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        OverTimeEntity overTimeEntity = new OverTimeEntity();

        overTimeEntity.setRequestId(generateId());
        overTimeEntity.setEmployee(employeeById.get());
        overTimeEntity.setRequestDate(dto.getRequestDate());
        overTimeEntity.setText(dto.getText());
        overTimeEntity.setDate(dto.getDate());
        overTimeEntity.setStartTime(dto.getStartTime());
        overTimeEntity.setEndTime(dto.getEndTime());
        overTimeEntity.setStatusEnum(StatusEnum.PENDING);

        //calculate and set OT payment
        Duration duration = Duration.between(dto.getStartTime(), dto.getEndTime());
        BigDecimal overtimeSalaryPerHour = employeeById.get().getJobRole().getSalaryPolicy().getOvertimeSalaryPerHour();

        overTimeEntity.setPaymentAmount(calculateOTSalary(duration,overtimeSalaryPerHour).doubleValue());

        overTimeRepository.save(overTimeEntity);

        result.put(key1, true);
        result.put(key2, "Request recorded successfully.");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> manageRequest(OverTimeApproval dto) {
        Map<String, Object> result = new HashMap<>();
        String key1 = "status";
        String key2 = "message";

        Optional<EmployeeEntity> managerById = employeeRepository.findById(dto.getManagerId());

        if (managerById.isEmpty()){
            result.put(key1, false);
            result.put(key2, "Manager not found.");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        Optional<OverTimeEntity> overTimeEntityById = overTimeRepository.findById(dto.getRequestId());

        if (overTimeEntityById.isEmpty()){
            result.put(key1, false);
            result.put(key2, "Request not found.");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        OverTimeEntity overTimeEntity = overTimeEntityById.get();

        overTimeEntity.setManager(managerById.get());
        overTimeEntity.setApprovedDateTime(dto.getApprovedDateTime());
        overTimeEntity.setStatusEnum(dto.getStatusEnum());

        overTimeRepository.save(overTimeEntity);

        sendMessage(dto);

        if (StatusEnum.APPROVED == dto.getStatusEnum()){
            result.put(key1, true);
            result.put(key2, "Request approved successfully.");
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        result.put(key1, true);
        result.put(key2, "Request rejected successfully.");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }



    @Override
    public List<OverTimeRequestRead> getAllByStatus(String requesterId, StatusEnum statusEnum) {
        String branchId = employeeRepository.findBranchByEmployeeId(requesterId).getBranchId();
        String branchName = branchRepository.findById(branchId).get().getName();

        List<OverTimeRequestRead> requests = new ArrayList<>();

        for (OverTimeEntity byStatus : overTimeRepository.findByStatus(statusEnum)) {

            if (Objects.equals(branchId,
                    employeeRepository.findBranchByEmployeeId(
                            byStatus.getEmployee().getEmployeeId()).getBranchId())){

                OverTimeRequestRead requestRead = mapper.convertValue(
                        byStatus, OverTimeRequestRead.class);

                requestRead.getEmployee().setBranchName(branchName);

                requestRead.getEmployee().setJobRoleTitle(
                        byStatus.getEmployee().getJobRole().getTitle()
                );

                requests.add(requestRead);
            }
        }
        return requests;
    }

    @Override
    public List<OverTimeRequestRead> getAll(String requesterId) {
        String branchId = employeeRepository.findBranchByEmployeeId(requesterId).getBranchId();
        String branchName = branchRepository.findById(branchId).get().getName();

        List<OverTimeRequestRead> requests = new ArrayList<>();

        for (OverTimeEntity byStatus : overTimeRepository.findAll()) {

            if (Objects.equals(branchId,
                    employeeRepository.findBranchByEmployeeId(
                            byStatus.getEmployee().getEmployeeId()).getBranchId())){

                OverTimeRequestRead requestRead = mapper.convertValue(byStatus, OverTimeRequestRead.class);

                requestRead.getEmployee().setBranchName(branchName);

                requestRead.getEmployee().setJobRoleTitle(
                        byStatus.getEmployee().getJobRole().getTitle()
                );

                requests.add(requestRead);
            }
        }
        return requests;
    }

    @Override
    public OverTimeRequestRead getById(String requestId) {
        Optional<OverTimeEntity> byId = overTimeRepository.findById(requestId);

        if (byId.isEmpty()){
            return null;
        }

        OverTimeRequestRead request = mapper.convertValue(byId.get(), OverTimeRequestRead.class);

        request.getEmployee().setBranchName(
                byId.get().getEmployee().getBranch().getName()
        );

        request.getEmployee().setJobRoleTitle(
                byId.get().getEmployee().getJobRole().getTitle()
        );

        return request;
    }


    private void sendMessage(OverTimeApproval dto) {
        Optional<OverTimeEntity> requestById = overTimeRepository.findById(dto.getRequestId());

        if (requestById.isEmpty()){
            return;
        }

        MessageEntity messageEntity = new MessageEntity();

        LocalDateTime approvedDateTime = dto.getApprovedDateTime();
        // Extract date and time from LocalDateTime
        LocalDate date = approvedDateTime.toLocalDate();
        LocalTime time = approvedDateTime.toLocalTime();
        // Set date and time to messageEntity
        messageEntity.setDate(date);
        messageEntity.setTime(time);

        // Set receiver and text
        messageEntity.setReceiver(requestById.get().getEmployee());

        Double paymentAmount = requestById.get().getPaymentAmount();

        if (dto.getStatusEnum() == StatusEnum.REJECTED){
            paymentAmount = 0.0;
        }

        messageEntity.setText(
                "Overtime request on " + requestById.get().getDate() +
                        " from " + requestById.get().getStartTime() +
                        " to " + requestById.get().getEndTime() +
                        " " + dto.getStatusEnum()+
                        ". Payment = Rs. " + paymentAmount);

        // Save messageEntity to repository
        messageRepository.save(messageEntity);
    }
    private String generateId(){
        String maxId = overTimeRepository.findMaxOverTimeRequestId();

        if (maxId == null){
            return "OTR0001";
        }

        String numberPart = maxId.replaceAll("\\D+", "");
        int number = Integer.parseInt(numberPart);
        number++;
        return "OTR" + String.format("%04d", number);
    }

    private BigDecimal calculateOTSalary(Duration duration, BigDecimal overtimeSalaryPerHour){
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;

        // Convert minutes and seconds to the fractional part of an hour
        BigDecimal minutesFraction = BigDecimal.valueOf(minutes).divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);
        BigDecimal secondsFraction = BigDecimal.valueOf(seconds).divide(BigDecimal.valueOf(3600), 2, RoundingMode.HALF_UP);

        // Calculate total hours as BigDecimal
        BigDecimal totalHours = BigDecimal.valueOf(hours).add(minutesFraction).add(secondsFraction);
        // Calculate total overtime salary
        BigDecimal totalOvertimeSalary = totalHours.multiply(overtimeSalaryPerHour);

        return totalOvertimeSalary.setScale(2, RoundingMode.HALF_UP);
    }

}
