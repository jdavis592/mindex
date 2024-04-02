package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeService employeeService;

    /*
     * create implementation will work as follows:
     * Create new compensation identifier for new compensation object
     * If employee exists, grab from database. Else, create one.
     * Set effective date = current date
     * Set salary = request salary amount. If it doesn't exist, throw exception
     */
    @Override
    public Compensation create(Compensation compensation) {
        LOG.debug("Creating compensation data object from request [{}]", compensation);
        // Create new identifier for compensation object
        compensation.setCompensationId(UUID.randomUUID().toString());

        // If employee object after querying the repository is null, throw exception
        if (compensation.getEmployee() == null) {
            throw new RuntimeException("Must have employee information to create Compensation data.");
        }
        // If  salary is null, throw exception
        if (compensation.getSalary() == null) {
            throw new RuntimeException("Must have a salary to create Compensation data.");
        }

        // Set the employee data based on id results from employee table
        compensation.setEmployee(employeeRepository.findByEmployeeId(compensation.getEmployee().getEmployeeId()));

        // Create employee object if information is provided and is not existing employee
        if (compensation.getEmployee() != null) {
            if(compensation.getEmployee().getEmployeeId() == null && validateEmployeeRequestInformation(compensation.getEmployee())) {
                compensation.setEmployee(employeeService.create(compensation.getEmployee()));
            }
        }

        // set date to current date every time we create a salary
        compensation.setEffectiveDate(LocalDate.now());

        // Insert into compensation repository
        compensationRepository.insert(compensation);

        return compensation;
    }

    private boolean validateEmployeeRequestInformation(Employee employee) {
        return (employee.getFirstName() != null && employee.getLastName() != null && employee.getPosition() != null && employee.getDepartment() != null);
    }

    // Reads compensation data by finding the unique compensation identifier assigned upon creation.
    @Override
    public Compensation read(String compensationId) {
        LOG.debug("Reading compensation data object [{}]", compensationId);

        Compensation resp =  compensationRepository.findByCompensationId(compensationId);

        if (resp == null) {
            throw new RuntimeException("Invalid compensationId: " + compensationId);
        }

        return resp;
    }
}
