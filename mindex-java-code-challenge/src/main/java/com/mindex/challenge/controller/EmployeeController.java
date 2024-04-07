package com.mindex.challenge.controller;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("")
    @ResponseBody
    public ResponseEntity<Employee> create(@RequestBody @Valid Employee employee) {
        LOG.debug("Received employee create request for {}", employee);

        return new ResponseEntity<>(employeeService.create(employee), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> read(@PathVariable String id) {
        LOG.debug("Received employee create request for id {}", id);

        return new ResponseEntity<>(employeeService.read(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Employee> update(@PathVariable String id, @RequestBody Employee employee) {
        LOG.debug("Received employee create request for id {} and employee {}", id, employee);

        employee.setEmployeeId(id);
        return new ResponseEntity<>(employeeService.update(employee), HttpStatus.OK);
    }
}
