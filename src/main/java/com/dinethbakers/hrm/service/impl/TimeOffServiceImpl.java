package com.dinethbakers.hrm.service.impl;

import com.dinethbakers.hrm.entity.EmployeeEntity;
import com.dinethbakers.hrm.entity.MessageEntity;
import com.dinethbakers.hrm.entity.TimeOffEntity;
import com.dinethbakers.hrm.model.TimeOffApproval;
import com.dinethbakers.hrm.model.TimeOffRequest;
import com.dinethbakers.hrm.model.TimeOffRequestRead;
import com.dinethbakers.hrm.repository.jparepository.BranchRepository;
import com.dinethbakers.hrm.repository.jparepository.EmployeeRepository;
import com.dinethbakers.hrm.repository.jparepository.MessageRepository;
import com.dinethbakers.hrm.repository.jparepository.TimeOffRepository;
import com.dinethbakers.hrm.service.TimeOffService;
import com.dinethbakers.hrm.util.StatusEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TimeOffServiceImpl implements TimeOffService {
    private final TimeOffRepository timeOffRepository;
    private final EmployeeRepository employeeRepository;
    private final BranchRepository branchRepository;
    private final MessageRepository messageRepository;
    private final ObjectMapper mapper;

    @Override
    public ResponseEntity<Map<String, Object>> persistRequest(TimeOffRequest dto) {
        Map<String, Object> result = new HashMap<>();
        String key1 = "status";
        String key2 = "message";


        Optional<EmployeeEntity> employeeById = employeeRepository.findById(dto.getEmployeeId());

        if (employeeById.isEmpty()){
            result.put(key1, false);
            result.put(key2, "Employee not found.");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        TimeOffEntity timeOffEntity = new TimeOffEntity();

        timeOffEntity.setRequestId(generateId());
        timeOffEntity.setEmployee(employeeById.get());
        timeOffEntity.setText(dto.getText());
        timeOffEntity.setRequestDateTime(dto.getRequestDateTime());
        timeOffEntity.setStartDateTime(dto.getStartDateTime());
        timeOffEntity.setEndDateTime(dto.getEndDateTime());
        timeOffEntity.setStatusEnum(StatusEnum.PENDING);

        timeOffRepository.save(timeOffEntity);

        result.put(key1, true);
        result.put(key2, "Request recorded successfully.");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> manageRequest(TimeOffApproval dto) {
        Map<String, Object> result = new HashMap<>();
        String key1 = "status";
        String key2 = "message";


        Optional<EmployeeEntity> managerById = employeeRepository.findById(dto.getManagerId());

        if (managerById.isEmpty()){
            result.put(key1, false);
            result.put(key2, "Manager not found.");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        Optional<TimeOffEntity> timeOffEntityById = timeOffRepository.findById(dto.getRequestId());

        if (timeOffEntityById.isEmpty()){
            result.put(key1, false);
            result.put(key2, "Request not found.");
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

        TimeOffEntity timeOffEntity = timeOffEntityById.get();

        timeOffEntity.setManager(managerById.get());
        timeOffEntity.setApprovedDateTime(dto.getApprovedDateTime());
        timeOffEntity.setStatusEnum(dto.getStatusEnum());

        timeOffRepository.save(timeOffEntity);

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
    public List<TimeOffRequestRead> getAllByStatus(String requesterId, StatusEnum statusEnum) {
        String branchId = employeeRepository.findBranchByEmployeeId(requesterId).getBranchId();
        String branchName = branchRepository.findById(branchId).get().getName();

        List<TimeOffRequestRead> requests = new ArrayList<>();

        for (TimeOffEntity byStatus : timeOffRepository.findByStatus(statusEnum)) {

            if (Objects.equals(branchId,
                    employeeRepository.findBranchByEmployeeId(
                            byStatus.getEmployee().getEmployeeId()).getBranchId())){

                TimeOffRequestRead requestRead = mapper.convertValue(byStatus, TimeOffRequestRead.class);

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
    public List<TimeOffRequestRead> getAll(String requesterId) {
        String branchId = employeeRepository.findBranchByEmployeeId(requesterId).getBranchId();
        String branchName = branchRepository.findById(branchId).get().getName();

        List<TimeOffRequestRead> requests = new ArrayList<>();

        for (TimeOffEntity byStatus : timeOffRepository.findAll()) {

            if (Objects.equals(branchId,
                    employeeRepository.findBranchByEmployeeId(
                            byStatus.getEmployee().getEmployeeId()).getBranchId())){

                TimeOffRequestRead requestRead = mapper.convertValue(byStatus, TimeOffRequestRead.class);

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
    public TimeOffRequestRead getById(String requestId) {
        Optional<TimeOffEntity> byId = timeOffRepository.findById(requestId);

        if (byId.isEmpty()){
            return null;
        }

        TimeOffRequestRead request = mapper.convertValue(byId.get(), TimeOffRequestRead.class);

        request.getEmployee().setBranchName(
                byId.get().getEmployee().getBranch().getName()
        );

        request.getEmployee().setJobRoleTitle(
                byId.get().getEmployee().getJobRole().getTitle()
        );

        return request;
    }

    private void sendMessage(TimeOffApproval dto) {
        Optional<TimeOffEntity> requestById = timeOffRepository.findById(dto.getRequestId());

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

        LocalDateTime startDateTime = requestById.get().getStartDateTime();
        // Extract date and time from LocalDateTime
        LocalDate startDate = startDateTime.toLocalDate();
        LocalTime startTime = startDateTime.toLocalTime();
        // Set date and time to messageEntity
        messageEntity.setDate(date);
        messageEntity.setTime(time);

        LocalDateTime endDateTime = requestById.get().getEndDateTime();
        // Extract date and time from LocalDateTime
        LocalDate endDate = endDateTime.toLocalDate();
        LocalTime endTime = endDateTime.toLocalTime();
        // Set date and time to messageEntity
        messageEntity.setDate(date);
        messageEntity.setTime(time);

        // Set receiver and text
        messageEntity.setReceiver(requestById.get().getEmployee());


        messageEntity.setText(
                "Time Off request" +
                        " from " + startTime + " on " + startDate +
                        " to " + endTime + " on " + endDate +
                        " " + dto.getStatusEnum());

        // Save messageEntity to repository
        messageRepository.save(messageEntity);
    }

    private String generateId(){
        String maxId = timeOffRepository.findMaxTimeOffRequestId();

        if (maxId == null){
            return "TOR0001";
        }

        String numberPart = maxId.replaceAll("\\D+", "");
        int number = Integer.parseInt(numberPart);
        number++;
        return "TOR" + String.format("%04d", number);
    }

}
