package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import jdk.vm.ci.meta.Local;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CompensationServiceImplTest {

    @Mock
    CompensationRepository compensationRepository;

    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    EmployeeService employeeService;

    @InjectMocks
    private CompensationServiceImpl compensationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCompensationByEmployeeId_Success() {
        Compensation testCompensation = new Compensation();
        String employeeId = "testCompensation";
        testCompensation.setEmployeeId(employeeId);
        testCompensation.setSalary(50000.00);
        testCompensation.setEffectiveDate(LocalDate.now());

        when(compensationService.getCompensationByEmployeeId(employeeId)).thenReturn(testCompensation);
        when(compensationRepository.findByEmployeeId(employeeId)).thenReturn(testCompensation);

        Compensation fetchedCompensation = compensationService.getCompensationByEmployeeId(employeeId);

        assertNotNull(fetchedCompensation);
        assertCompensationEquivalence(testCompensation, fetchedCompensation);
    }

    @Test
    public void testCreateCompensation_Success() {
        String employeeId = "testCompensation";
        Double salary = 50000.00;
        LocalDate effectiveDate = LocalDate.now();

        Compensation testCompensation = new Compensation();
        testCompensation.setEmployeeId(employeeId);
        testCompensation.setSalary(salary);
        testCompensation.setEffectiveDate(LocalDate.now());

        Employee testEmployee = new Employee();
        testEmployee.setEmployeeId(employeeId);

        when(employeeService.read(employeeId)).thenReturn(testEmployee);
        when(employeeRepository.findByEmployeeId(employeeId)).thenReturn(testEmployee);
        when(compensationService.createCompensation(employeeId, salary, effectiveDate)).thenReturn(testCompensation);
        doReturn(testCompensation).when(compensationRepository).save(testCompensation);

       Compensation createCompensation = compensationService.createCompensation(employeeId, salary, effectiveDate);

        assertNotNull(createCompensation);
        assertCompensationEquivalence(testCompensation, createCompensation);
    }

    private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        Assert.assertEquals(expected.getEmployeeId(), actual.getEmployeeId());
        Assert.assertEquals(expected.getSalary(), actual.getSalary());
        Assert.assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
    }
}
