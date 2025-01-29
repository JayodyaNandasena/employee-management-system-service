package com.dinethbakers.hrm.service.impl;

import com.dinethbakers.hrm.entity.UserEntity;
import com.dinethbakers.hrm.entity.BranchEntity;
import com.dinethbakers.hrm.entity.EmployeeEntity;
import com.dinethbakers.hrm.entity.JobRoleEntity;
import com.dinethbakers.hrm.model.AccountCreate;
import com.dinethbakers.hrm.model.EmployeeCreate;
import com.dinethbakers.hrm.model.EmployeeRead;
import com.dinethbakers.hrm.repository.jparepository.UserRepository;
import com.dinethbakers.hrm.repository.jparepository.BranchRepository;
import com.dinethbakers.hrm.repository.jparepository.EmployeeRepository;
import com.dinethbakers.hrm.repository.jparepository.JobRoleRepository;
import com.dinethbakers.hrm.repository.nativerepository.AccountNativeRepository;
import com.dinethbakers.hrm.repository.nativerepository.EmployeeNativeRepository;
import com.dinethbakers.hrm.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeNativeRepository employeeNativeRepository;
    private final UserRepository userRepository;
    private final AccountNativeRepository accountNativeRepository;
    private final BranchRepository branchRepository;
    private final JobRoleRepository jobRoleRepository;
    private final ObjectMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    @Override
    public EmployeeRead persist(EmployeeCreate dto) {
        EmployeeEntity entity = mapper.convertValue(dto, EmployeeEntity.class);

        entity.setEmployeeId(generateId());
        entity.setBranch(getBranchByName(dto.getBranchName()));
        entity.setJobRole(getJobRoleByTitle(dto.getJobRoleTitle()));

        EmployeeEntity savedEntity = employeeRepository.save(entity);

        EmployeeRead savedEmployee = mapper.convertValue(savedEntity, EmployeeRead.class);

        persistAccount(savedEntity.getEmployeeId(), dto.getAccount());

        return savedEmployee;
    }

    @Override
    public EmployeeCreate update(EmployeeCreate dto) {
        EmployeeEntity entity = mapper.convertValue(dto, EmployeeEntity.class);
        entity.setBranch(getBranchByName(dto.getBranchName()));
        entity.setJobRole(getJobRoleByTitle(dto.getJobRoleTitle()));

        //update employee table
        EmployeeEntity editedEntity = employeeNativeRepository.editEmployee(entity);

        EmployeeCreate savedEmployee = mapper.convertValue(editedEntity, EmployeeCreate.class);

        //update account table

        UserEntity userEntity = mapper.convertValue(dto.getAccount(), UserEntity.class);
        userEntity.setEmployee(entity);
        //userEntity.setPassword(BCrypt.hashpw(dto.getAccount().getPassword(), BCrypt.gensalt()));

        savedEmployee.setAccount(
                mapper.convertValue(
                        accountNativeRepository.editAccount(userEntity),
                        AccountCreate.class)
        );

        savedEmployee.setBranchName(editedEntity.getBranch().getName());

        savedEmployee.setJobRoleTitle(
                editedEntity.getJobRole().getTitle()
        );
        return savedEmployee;
    }

    @Override
    public ResponseEntity<EmployeeCreate> getById(String id) {
        Optional<EmployeeEntity> byId = employeeRepository.findById(id);

        if (byId.isPresent()) {
            EmployeeCreate employee = mapper.convertValue(byId, EmployeeCreate.class);
            employee.setAccount(
                    mapper.convertValue(
                            userRepository.findByEmployeeEmployeeId(id),
                            AccountCreate.class)
            );

            employee.setBranchName(byId.get().getBranch().getName());
            employee.setJobRoleTitle(byId.get().getJobRole().getTitle());

            return ResponseEntity.ok(employee);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }


    private String generateId(){
        String maxId = employeeRepository.findMaxEmployeeId();

        if (maxId == null){
            return "E001";
        }

        String numberPart = maxId.replaceAll("\\D+", "");
        int number = Integer.parseInt(numberPart);
        number++;
        return "E" + String.format("%03d", number);
    }
    private AccountCreate persistAccount(String employeeId, AccountCreate accountCreate){

        Optional<EmployeeEntity> byId = employeeRepository.findById(employeeId);

//        UserEntity userEntity = mapper.convertValue(accountCreate, UserEntity.class);

        if (byId.isPresent()) {
//            userEntity.setEmployee(byId.get());
//            userEntity.setPassword(BCrypt.hashpw(accountCreate.getPassword(), BCrypt.gensalt()));

//            UserEntity user = new UserEntity
//                    .setUsername(accountCreate.getUsername())
//                    .setPassword(passwordEncoder.encode(accountCreate.getPassword()))
//                    .setEmployee(byId.get());

            UserEntity user = new UserEntity();
            user.setUsername(accountCreate.getUsername());
            user.setPassword(passwordEncoder.encode(accountCreate.getPassword()));
            user.setEmployee(byId.get());

            return mapper.convertValue(
                    userRepository.save(user), AccountCreate.class
            );
        }

        throw new NotFoundException("employee not found");
    }

    private BranchEntity getBranchByName(String name){
        Optional<BranchEntity> branchByName = branchRepository.findByName(name);
        return branchByName.orElse(null);
    }

    private JobRoleEntity getJobRoleByTitle(String title){
        Optional<JobRoleEntity> jobRoleByTitle = jobRoleRepository.findByTitle(title);
        return jobRoleByTitle.orElse(null);
    }
}
