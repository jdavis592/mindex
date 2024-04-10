package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationControllerTest {
    private String createCompensationUrl;
    private String getCompensationByEmployoeeIdUrl;

    @Autowired
    private CompensationController compensationController;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        createCompensationUrl = "http://localhost:" + port + "/compensation/";
        getCompensationByEmployoeeIdUrl = "http://localhost:" + port + "/compensation/{id}";
    }

    @Test
    public void testCreateCompensation_Success() {
        Compensation testCompensation = new Compensation();
        // Using Paul's employee ID since I'm not mocking the endpoint
        testCompensation.setEmployeeId("b7839309-3348-463b-a7e3-5de1c168beb3");
        testCompensation.setSalary(50000.00);
        testCompensation.setEffectiveDate(LocalDate.now());

        Compensation createdCompensation = restTemplate.postForEntity(createCompensationUrl, testCompensation, Compensation.class).getBody();

        assertNotNull(createdCompensation.getEmployeeId());
        assertCompensationEquivalence(testCompensation, createdCompensation);

    }

    @Test
    public void testGetCompensationByEmployeeId_Success() {
        Compensation testCompensation = new Compensation();
        // Using compensation object created within the database since I'm not mocking the endpoint
        testCompensation.setEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");
        testCompensation.setSalary(100000.00);
        testCompensation.setEffectiveDate(LocalDate.parse("2024-04-29"));

        Compensation fetchedCompensation = restTemplate.getForEntity(getCompensationByEmployoeeIdUrl, Compensation.class, testCompensation.getEmployeeId()).getBody();

        assertCompensationEquivalence(testCompensation, fetchedCompensation);
    }

    private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        assertEquals(expected.getEmployeeId(), actual.getEmployeeId());
        assertEquals(expected.getSalary(), actual.getSalary());
        assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
    }
}