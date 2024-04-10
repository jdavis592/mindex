package com.mindex.challenge.service;

import com.mindex.challenge.data.ReportingStructure;
import org.springframework.stereotype.Service;

@Service
public interface ReportingStructureService {
    ReportingStructure GetReportingStructure(String employeeId);
}
