package com.mindex.challenge.controller;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ReportingStructureController {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureController.class);

    private final ReportingStructureService reportingStructureService;

    @Autowired
    public ReportingStructureController(ReportingStructureService reportingStructureService) {
        this.reportingStructureService = reportingStructureService;
    }

    @GetMapping("/employee/reportingStructure/{id}")
    @Valid
    public ResponseEntity<ReportingStructure> getReportingStructure(@PathVariable String id) {
        LOG.debug("Received GetReportingStructure request for id{}", id);

        return new ResponseEntity<>(reportingStructureService.GetReportingStructure(id), HttpStatus.OK);
    }
}
