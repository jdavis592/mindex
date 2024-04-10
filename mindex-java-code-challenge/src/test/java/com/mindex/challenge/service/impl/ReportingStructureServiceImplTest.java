package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReportingStructureServiceImplTest {

    @InjectMocks
    private ReportingStructureServiceImpl reportingStructureService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetReportingStructure() {
        // Create test employee
        Employee testEmployee = new Employee();
        testEmployee.setEmployeeId("testEmployeeId");

        // Create direct reports for the test employee
        Employee directReport1 = new Employee();
        directReport1.setEmployeeId("directReport1Id");
        Employee directReport2 = new Employee();
        directReport2.setEmployeeId("directReport2Id");
        List<Employee> directReports = Arrays.asList(directReport1, directReport2);
        testEmployee.setDirectReports(directReports);

        // Mock behavior of employee repository based on reporting structure implementation. Interesting fact here - when
        // testing recursive statements, ensure you create a when statement for each iteration you expect to see. Otherwise,
        // you will get an NPE and return confused all-the-while questioning life choices :')
        when(employeeRepository.findByEmployeeId(testEmployee.getEmployeeId())).thenReturn(testEmployee);
        when(employeeRepository.findByEmployeeId(directReport1.getEmployeeId())).thenReturn(directReport1);
        when(employeeRepository.findByEmployeeId(directReport2.getEmployeeId())).thenReturn(directReport2);

        // Call the service method
        ReportingStructure reportingStructure = reportingStructureService.GetReportingStructure(testEmployee.getEmployeeId());

        // Relevant Assertions
        assertNotNull(reportingStructure);
        assertEquals(2, reportingStructure.getNumberOfReports());
    }
}
