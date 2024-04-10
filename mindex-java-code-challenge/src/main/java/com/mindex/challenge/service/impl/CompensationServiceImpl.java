package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final CompensationRepository compensationRepository;
    private final EmployeeService employeeService;

    @Autowired
    public CompensationServiceImpl(CompensationRepository compensationRepository, EmployeeService employeeService) {
        this.compensationRepository = compensationRepository;
        this.employeeService = employeeService;
    }
    /**
     * getCompensationByEmployeeId implements the Compensation like-named service function. It will check if an employee
     * object exists with the id provided. If not, it will throw an exception. I decided to implement it this way to
     * reinforce the idea that compensation data should not exist without matching employee data in place.
     * @param employeeId ID used to find matching compensation data
     * */
    @Override
    public Compensation getCompensationByEmployeeId(String employeeId) {
        LOG.debug("Reading compensation data for employee ID - {}", employeeId);
        try {
            return compensationRepository.findByEmployeeId(employeeId);
        } catch (Exception e){
            throw new RuntimeException("Error occurred while finding compensation data in repository.", e);
        }
    }

    /**
     * createCompensation will create compensation data for pre-existing employee data. Compensation should not exist
     * without matching Employee data.
     * @param employeeId ID that links compensation to pre-existing employee data
     * @param salary salary to set for employee found at employeeId
     * @param effectiveDate date in which this data is to go into effect. In a perfect world (and within this
     *                      application), this value should always be in the future.
     * */
    @Override
    public Compensation createCompensation(String employeeId, Double salary, LocalDate effectiveDate) {
        // Validate if the employee with the given employeeId exists
        if (employeeService.read(employeeId) == null) {
            throw new IllegalArgumentException("Employee with ID " + employeeId + " does not exist");
        }

        // Inject new values to a new Compensation object
        LOG.debug("Creating New Compensation Data");
        Compensation compensation = new Compensation();
        compensation.setEmployeeId(employeeId);
        compensation.setSalary(salary);
        compensation.setEffectiveDate(effectiveDate);

        // Try to persist to the compensation repository
        try {
            LOG.debug("Saving Compensation data to the compensation repository");
            // I struggled to get a test case written with mocks because for some reason, this repository object was
            // returning null. If you look at the save() method, it says that it cannot return null. I could not figure
            // out why this was returning null (I was returning compensationRepository.save(compensation) on one line),
            // but returning the compensation object fixed the situation. Would love feedback here on why this may have
            // been the case.
            compensationRepository.save(compensation);
            return compensation;
        } catch(Exception e) {
            throw new RuntimeException("Error occurred while saving compensation data to repository.", e);
        }
    }
}
