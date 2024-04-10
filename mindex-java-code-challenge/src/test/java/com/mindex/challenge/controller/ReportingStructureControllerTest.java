package com.mindex.challenge.controller;

import com.mindex.challenge.data.ReportingStructure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureControllerTest {
    private String reportingStructureUrl;

    @Autowired
    private ReportingStructureController reportingStructureController;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        reportingStructureUrl = "http://localhost:" + port + "/employee/reportingStructure/{id}";
    }

    @Test
    public void testGetReportingStructure_Success() {
        String employeeId = "16a596ae-edd3-4847-99fe-c4518e82c86f";

        ReportingStructure createdReportingStructure = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, employeeId).getBody();

        assertNotNull(createdReportingStructure);
        assertEquals(4, createdReportingStructure.getNumberOfReports());
        assertEquals(employeeId,
                createdReportingStructure.getEmployee().getEmployeeId());
    }
}
