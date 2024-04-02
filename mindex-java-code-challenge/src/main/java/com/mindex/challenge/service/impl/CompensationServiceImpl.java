package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
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

    @Autowired
    private CompensationRepository compensationRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeService employeeService;

    /*
     * create implementation will work as follows:
     * If employee exists, grab from database. Else, call create employee.
     * Set effective date = current date
     * Set salary = request salary amount. If it doesn't exist, throw exception
     */
    @Override
    public Compensation create(Compensation req) {
        LOG.debug("Creating compensation data object from request [{}]", req);
        Compensation resp = new Compensation();
        resp.setEmployee(employeeRepository.findByEmployeeId(req.getEmployee().getEmployeeId()));

        // Employee handling logic
        if (resp.getEmployee() != null) {
            resp.setEmployee(req.getEmployee());
        } else {
            // Since the create endpoint establishes a new ID for employee, it's okay
            // if ID here is null
            employeeService.create(req.getEmployee());
        }

        if (req.getSalary() == null) {
            throw new RuntimeException("Must have a salary to create Compensation data.");
        } else {
            resp.setSalary(req.getSalary());
        }
        resp.setEffectiveDate(LocalDate.now());

        compensationRepository.insert(resp);

        return resp;
    }

    @Override
    public Compensation read(String id) {
        LOG.debug("Creating compensation data object from employee ID [{}]", id);

        Compensation resp =  compensationRepository.findByEmployeeId(id);

        if (resp == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return resp;
    }
}
