package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    // TODO: Make less calls to the repository. Current structure will make multiple calls to repository

    /*
    * CalculateNumberOfReports is a recursive function that will take an employeeID and calculate the number of
    * direct and distinct employees that work under the original employee and return it for use.
    */
    private int calculateNumberOfReports(String employeeId){
        // Assume the employee is a bossEmployee
        Employee bossEmployee = employeeRepository.findByEmployeeId(employeeId);
        // Grab bossEmployee's direct reports
        List<Employee> directReports = bossEmployee.getDirectReports();
        // initialize number of reports
        int numberOfReports = 0;
        // If directReports exist, set the number of reports to its size
        if(directReports != null) {
            numberOfReports = directReports.size();
        }
        // Establish base case for Recursion (no direct reports means employee is not a boss)
        if (directReports == null) {
            numberOfReports = 0;
        } else {
            // If employee has direct reports, loop through them and calculate if they have reports
            for(Employee employee: directReports) {
                numberOfReports += calculateNumberOfReports(employee.getEmployeeId());
            }
        }
        return numberOfReports;
    }

    @Override
    public ReportingStructure GetReportingStructure(String employeeId) {
        ReportingStructure result = new ReportingStructure();
        LOG.debug("Creating reporting structure for employee ID [{}]", employeeId);

        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        int numberOfReports = calculateNumberOfReports(employee.getEmployeeId());

        result.setEmployee(employee);
        result.setNumberOfReports(numberOfReports);

        LOG.debug("Reporting Structure creation complete for employee ID - {}", employeeId);
        return result;
    }
}
