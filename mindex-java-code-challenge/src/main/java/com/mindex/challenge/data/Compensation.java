package com.mindex.challenge.data;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * Compensation data object consists of 3 things: EmployeeId, salary, and effectiveDate. I went with implementing solely
 * an employeeId here instead of a whole EmployeeId because I thought having an entire employee object within the
 * confines of two repositories was unnecessary. In order to create a connection between the data types, we use a
 * specific employeeId related solely to the employee we query for. Spring takes care of validation with the set
 * annotations.
 **/
@Getter @Setter @NotNull @NoArgsConstructor @AllArgsConstructor
public class Compensation {
    private String employeeId;

    @NotNull(message = "Must provide salary with Compensation Data")
    @Min(value = 0L, message = "The value must be positive")
    private Double salary;

    @FutureOrPresent
    private LocalDate effectiveDate;
}