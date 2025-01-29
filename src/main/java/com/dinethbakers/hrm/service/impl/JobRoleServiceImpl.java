package com.dinethbakers.hrm.service.impl;

import com.dinethbakers.hrm.entity.*;
import com.dinethbakers.hrm.model.*;
import com.dinethbakers.hrm.repository.jparepository.*;
import com.dinethbakers.hrm.service.JobRoleService;
import com.dinethbakers.hrm.util.ShiftTypeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class JobRoleServiceImpl implements JobRoleService {
    private final JobRoleRepository jobRoleRepository;
    private final SalaryPolicyRepository salaryPolicyRepository;
    private final LeavePolicyRepository leavePolicyRepository;
    private final ShiftPolicyRepository shiftPolicyRepository;

    private final ObjectMapper mapper;


    @Override
    public JobRole persist(JobRole dto) {

        JobRoleEntity jobRoleEntity = mapper.convertValue(dto, JobRoleEntity.class);

        jobRoleEntity.setJobRoleId(generateId());

        jobRoleEntity.setSalaryPolicy(
                findOrCreateSalaryPolicy(dto.getSalaryPolicy()));
        jobRoleEntity.setLeavePolicy(
                findOrCreateLeavePolicy(dto.getLeavePolicy()));
        jobRoleEntity.setShiftPolicies(findOrCreateShiftPolicies(
                        dto.getShiftTypeEnum(),
                        dto.getShiftPolicies()));

        return mapper.convertValue(
                jobRoleRepository.save(jobRoleEntity), JobRole.class) ;
    }

    @Override
    public List<JobRole> getAll() {
        Iterable<JobRoleEntity> entityList = jobRoleRepository.findAll();

        List<JobRole> all = new ArrayList<>();

        for (JobRoleEntity entity: entityList) {
            all.add(
                    mapper.convertValue(entity, JobRole.class)
            );
        }

        return all;
    }

    @Override
    public List<String> getAllTitles() {
        return jobRoleRepository.findAllTitles();
    }

    private String generateId(){
        String maxId = jobRoleRepository.findMaxJobRoleId();

        if (maxId == null){
            return "R001";
        }

        String numberPart = maxId.replaceAll("\\D+", "");
        int number = Integer.parseInt(numberPart);
        number++;
        return "R" + String.format("%03d", number);
    }

    private SalaryPolicyEntity findOrCreateSalaryPolicy(SalaryPolicy dto){
        SalaryPolicyEntity byAll =
                salaryPolicyRepository.
                        findByMonthlyBasicSalaryAndOvertimeSalaryPerHourAndEpfPercentageAndEtfPercentage(
                                dto.getMonthlyBasicSalary(),
                                dto.getOvertimeSalaryPerHour(),
                                dto.getEpfPercentage(),
                                dto.getEtfPercentage()
                        ).orElse(null);

        if (byAll != null){
            return byAll;
        }
        return salaryPolicyRepository.save(
                mapper.convertValue(dto, SalaryPolicyEntity.class));

    }

    private LeavePolicyEntity findOrCreateLeavePolicy(LeavePolicy dto){
        LeavePolicyEntity byAll = leavePolicyRepository.findByNoOfPTODays(
                dto.getNoOfPTODays())
                .orElse(null);

        if (byAll != null){
            return byAll;
        }

        return leavePolicyRepository.save(
                mapper.convertValue(dto, LeavePolicyEntity.class));

    }


    private List<ShiftPolicyEntity> findOrCreateShiftPolicies(
            ShiftTypeEnum shiftTypeEnum, List<ShiftPolicy> dtos){
        List<ShiftPolicyEntity> policyEntityList = new ArrayList<>();

        if (shiftTypeEnum == ShiftTypeEnum.FLEXIBLE_HOURS){
            ShiftPolicy dto = dtos.get(0);

            ShiftPolicyEntity byHours = shiftPolicyRepository
                    .findByTotalHours(dto.getTotalHours())
                    .orElse(null);

            if (byHours == null){
                dto.setStartTime(null);
                dto.setEndTime(null);

                ShiftPolicyEntity savedEntity = shiftPolicyRepository.save(
                        mapper.convertValue(dto, ShiftPolicyEntity.class));

                policyEntityList.add(savedEntity);
            }else {
                policyEntityList.add(byHours);
            }
        }else{
            for (ShiftPolicy policy: dtos) {
                ShiftPolicyEntity byAll = shiftPolicyRepository.findByStartTimeAndEndTime(
                                policy.getStartTime(),
                                policy.getEndTime())
                        .orElse(null);

                if (byAll != null){
                    policyEntityList.add(byAll);
                }else {
                    long hours = policy.getStartTime().until(policy.getEndTime(), ChronoUnit.HOURS);
                    long minutes = policy.getStartTime().until(policy.getEndTime(), ChronoUnit.MINUTES) % 60;
                    policy.setTotalHours(LocalTime.of((int) hours, (int) minutes));

                    policyEntityList.add(
                            shiftPolicyRepository.save(
                                    mapper.convertValue(policy, ShiftPolicyEntity.class))
                    );
                }
            }
        }
        return policyEntityList;
    }
}
