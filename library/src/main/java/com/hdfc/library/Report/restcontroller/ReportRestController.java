package com.hdfc.library.Report.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hdfc.library.Report.entity.Report;
import com.hdfc.library.Report.exception.ReportNotFoundException;
import com.hdfc.library.Report.service.ReportServiceImp;

@RestController
@RequestMapping("/api/v2/reports")
public class ReportRestController {

    @Autowired
    private ReportServiceImp reportServiceImp;

    public ReportRestController(ReportServiceImp reportServiceImp) {
        this.reportServiceImp = reportServiceImp;
    }

    @GetMapping("/getallreports")
    public List<Report> getAllReports() throws ReportNotFoundException {
        return reportServiceImp.getAllReports();
    }

    @GetMapping("/getreport/{reportId}")
    public Report getReportById(Long reportId) throws ReportNotFoundException {
        return reportServiceImp.getReportById(reportId);
    }

    @DeleteMapping("/deletereport/{reportId}")
    public void deleteReportById(Long reportId) throws ReportNotFoundException {
        reportServiceImp.deleteReportById(reportId);
    }

}
