package com.mindex.challenge.service;

import com.mindex.challenge.data.Compensation;

import java.time.LocalDate;

/**
 * Interface for the compensation service. I mostly implemented this to match what was already given to me when initally
 * downloading the system.
 * */
public interface CompensationService {
    Compensation createCompensation(String employeeId, Double salary, LocalDate effectiveDate);
    Compensation getCompensationByEmployeeId(String id);
}
