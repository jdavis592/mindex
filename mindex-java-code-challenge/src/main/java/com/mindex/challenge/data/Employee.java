package com.mindex.challenge.data;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    private String employeeId;

    @NotEmpty(message = "first name is required")
    private String firstName;

    @NotEmpty(message = "last name is required")
    private String lastName;

    @NotEmpty(message = "must provide position")
    private String position;

    @NotEmpty(message = "must provide department")
    private String department;

    private List<Employee> directReports;
}
