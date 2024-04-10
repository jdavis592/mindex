package com.mindex.challenge.data;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportingStructure {
    @NotNull
    private Employee employee;
    private int numberOfReports;
}
