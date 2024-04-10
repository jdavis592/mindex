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

/**
 * This was actually my first time using the Mockito dependency for testing. I found it very interesting, especially for
 * the stuff regarding repository calls. That is a part of the reason I limited it to the repository calls (and my troubles
 * voiced in the comment field on the CompensationControllerTest file).
 * */
@RunWith(MockitoJUnitRunner.class)
public class CompensationServiceImplTest {

    // Mock annotation will mock the items that need to be injected
    @Mock
    CompensationRepository compensationRepository;

    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    EmployeeService employeeService;

    // Injects the Mock beans into this item as well as mocking it. Usually will be the item you're testing.
    @InjectMocks
    private CompensationServiceImpl compensationService;

    // Establishes the setup for the mock annotations from above.
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCompensationByEmployeeId_Success() {
        // Create test objects
        Compensation testCompensation = new Compensation();
        String employeeId = "testCompensation";
        testCompensation.setEmployeeId(employeeId);
        testCompensation.setSalary(50000.00);
        testCompensation.setEffectiveDate(LocalDate.now());

        // set up the mock expectations
        when(compensationService.getCompensationByEmployeeId(employeeId)).thenReturn(testCompensation);
        when(compensationRepository.findByEmployeeId(employeeId)).thenReturn(testCompensation);

        // runs the logic you want to test
        Compensation fetchedCompensation = compensationService.getCompensationByEmployeeId(employeeId);

        // Valid assertions
        assertNotNull(fetchedCompensation);
        assertCompensationEquivalence(testCompensation, fetchedCompensation);
    }

    @Test
    public void testCreateCompensation_Success() {
        // Creates test objects
        String employeeId = "testCompensation";
        Double salary = 50000.00;
        LocalDate effectiveDate = LocalDate.now();

        Compensation testCompensation = new Compensation();
        testCompensation.setEmployeeId(employeeId);
        testCompensation.setSalary(salary);
        testCompensation.setEffectiveDate(LocalDate.now());

        Employee testEmployee = new Employee();
        testEmployee.setEmployeeId(employeeId);

        // Valid assertions
        when(employeeService.read(employeeId)).thenReturn(testEmployee);
        when(employeeRepository.findByEmployeeId(employeeId)).thenReturn(testEmployee);
        when(compensationService.createCompensation(employeeId, salary, effectiveDate)).thenReturn(testCompensation);
        doReturn(testCompensation).when(compensationRepository).save(testCompensation);

        // Run test
       Compensation createCompensation = compensationService.createCompensation(employeeId, salary, effectiveDate);

       // Relevant assertions. I specifically had issues with the assertNotNull here that I describe about in more detail
       // on the CompensationServiceImpl file. That was an interesting problem to work through!
        assertNotNull(createCompensation);
        assertCompensationEquivalence(testCompensation, createCompensation);
    }

    // Assertions helper function for the Compensation item
    private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        Assert.assertEquals(expected.getEmployeeId(), actual.getEmployeeId());
        Assert.assertEquals(expected.getSalary(), actual.getSalary());
        Assert.assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
    }
}
