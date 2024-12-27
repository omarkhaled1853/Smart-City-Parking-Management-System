package com.example.Backend.services;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Service
public class ReportService {

    @Autowired
    private DataSource dataSource;

    public String generateParkingLotReport() throws Exception {

        String jrxmlPath = "src/main/resources/reports/ParkingLotReport.jrxml";
        String jasperPath = "src/main/resources/reports/ParkingLotReport.jasper";
        String  outputFilePath=  "src/main/resources/reports/ParkingLotReport.pdf";
        // Compile JRXML to Jasper file
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlPath);
        JasperCompileManager.writeReportToXmlFile(jasperReport, jasperPath);

        try (Connection connection = dataSource.getConnection()) {
            // Fill the report with data from the database using the connection
            Map<String, Object> parameters = new HashMap<>();
            // You can add parameters to the report here if needed (e.g., filtering by ParkingLotID)
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

            // Export the report to PDF
            exportReportToPdf(jasperPrint, outputFilePath);
        }
        return "Parking Lot Report Done";
    }


    public String generateParkingSpotReport() throws Exception {
        String jrxmlPath = "src/main/resources/reports/ParkingSpotReport.jrxml";
        String jasperPath = "src/main/resources/reports/ParkingSpotReport.jasper";
        String outputFilePath = "src/main/resources/reports/ParkingSpotReport.pdf";

        // Compile JRXML to Jasper file
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlPath);

        // Generate the report
        try (Connection connection = dataSource.getConnection()) {
            // Fill the report with data from the database using the connection
            Map<String, Object> parameters = new HashMap<>();
            // You can add parameters to the report here if needed (e.g., filtering by ParkingLotID)
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

            // Export the report to PDF
            exportReportToPdf(jasperPrint, outputFilePath);
        }

        return "Parking Spot Report Done";
    }

    private void exportReportToPdf(JasperPrint jasperPrint, String outputFilePath) throws Exception {
        File outputFile = new File(outputFilePath);
        try (OutputStream outputStream = new FileOutputStream(outputFile)) {
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
            exporter.exportReport();
        }
    }

    public String TopUsersAndLotsReport() throws Exception {
        String jrxmlPath = "src/main/resources/reports/TopUsersAndLotsReport.jrxml";
        String jasperPath = "src/main/resources/reports/Top Users and Parking Lots Report.jasper";
        String outputFilePath = "src/main/resources/reports/TopUsersAndLotsReport.pdf";
            // Compile JRXML to Jasper file
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlPath);
        JasperCompileManager.writeReportToXmlFile(jasperReport, jasperPath);

        try (Connection connection = dataSource.getConnection()) {
            // Fill the report with data from the database using the connection
            Map<String, Object> parameters = new HashMap<>();
            // You can add parameters to the report here if needed (e.g., filtering by ParkingLotID)
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);

            // Export the report to PDF
            exportReportToPdf(jasperPrint, outputFilePath);
        }
        return "Top Users and Lots Report Generated Successfully";
    }

    
}