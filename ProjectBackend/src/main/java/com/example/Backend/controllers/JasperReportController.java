package com.example.Backend.controllers;

import com.example.Backend.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jasper")
public class JasperReportController {



    @Autowired
    private ReportService jasperReportService;

    public JasperReportController(ReportService jasperReportService) {
        this.jasperReportService = jasperReportService;
    }

    @GetMapping("/reports/generate-pdf")
    public String generatePdfReport() {
        try {
            return jasperReportService.TopUsersAndLotsReport();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

}

