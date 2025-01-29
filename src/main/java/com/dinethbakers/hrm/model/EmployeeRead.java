package com.dinethbakers.hrm.model;

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
    private GenderEnum genderEnum;
    private Branch branch;
    private JobRole jobRole;
    private AccountCreate account;
    //private List<EmployeeMobile> mobileNumbers;
    //private List<Attendance> attendanceRecords;
    //List<TimeOff> timeOffList;
    //private List<Message> messages;
}
