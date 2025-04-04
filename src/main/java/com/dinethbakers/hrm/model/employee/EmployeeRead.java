package com.dinethbakers.hrm.model.employee;

import com.dinethbakers.hrm.model.jobRole.JobRole;
import com.dinethbakers.hrm.model.account.AccountRead;
import com.dinethbakers.hrm.model.branch.Branch;
import com.dinethbakers.hrm.util.GenderEnum;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeRead {
    private String employeeId;
    private String firstName;
    private String lastName;
    private String nic;
    private LocalDate dob;
    private String profilePicture;
    private LocalDate hiredDate;
    private String address;
    private String email;
    private GenderEnum gender;
    private Branch branch;
    private JobRole jobRole;
    private AccountRead account;
    //private List<EmployeeMobile> mobileNumbers;
    //private List<Attendance> attendanceRecords;
    //List<TimeOff> timeOffList;
    //private List<Message> messages;
}
