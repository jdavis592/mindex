package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * CompensationController dictates HTTP data traffic for all things regarding Compensation.
 * */
@RestController
@RequestMapping("/compensation")
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    /**
     * getCompensationByEmployeeId extracts employee id from the path variable in request to find matching compensation
     * data. If none exists, respond with a 404, otherwise return 200.
     * @param id Employee ID used to map the compensation object to an employee object
     * */
    @GetMapping("/{id}")
    public ResponseEntity<Compensation> getCompensationByEmployeeId(@PathVariable String id) {
        LOG.debug("Received compensation getCompensationByEmployeeId request for employee {}", id);
        Compensation compensation = compensationService.getCompensationByEmployeeId(id);

        if(compensation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(compensation, HttpStatus.OK);
        }
    }

    /**
     * createCompensation will create a compensation object based on the request sent through the endpoint.
     * */
    @PostMapping @Valid
    public ResponseEntity<Compensation> createCompensation(
            @RequestBody @Valid Compensation compensation) {
        LOG.debug("Received compensation create request");

        return new ResponseEntity<>(compensationService.createCompensation(
                compensation.getEmployeeId(),
                compensation.getSalary(),
                compensation.getEffectiveDate()), HttpStatus.CREATED);
    }
}
