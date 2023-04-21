package com.hdfc.library.Report.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hdfc.library.Report.entity.Report;
import com.hdfc.library.Report.exception.ReportNotFoundException;
import com.hdfc.library.Report.repository.ReportRepository;

/*  getAllReports(): retrieves a list of all available reports.

    getReportById(Long reportId): retrieves a specific report by its ID.
    Throws a ReportNotFoundException if the report is not found.
    
    deleteReportById(Long reportId): deletes a specific report by its ID. 
    Throws a ReportNotFoundException if the report is not found */

@Service
public class ReportServiceImp implements IReportService {

    @Autowired
    private ReportRepository reportRepository;

    public ReportServiceImp(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public List<Report> getAllReports() {
        List<Report> reports = reportRepository.findAll();
        return reports.stream()
                .map(report -> new Report(report.getReportId(),
                        report.getUserActivity(),
                        report.getBookStatus(),
                        report.getTotalfine(),
                        report.getFinesCollected()))
                .collect(Collectors.toList());
    }

    @Override
    public Report getReportById(Long reportId) throws ReportNotFoundException {
        Optional<Report> reportOptional = reportRepository.findById(reportId);
        if (reportOptional.isPresent()) {
            Report report = reportOptional.get();
            return new Report(report.getReportId(),
                    report.getUserActivity(),
                    report.getBookStatus(),
                    report.getTotalfine(),
                    report.getFinesCollected());
        } else {
            throw new ReportNotFoundException("Report not found with id: " + reportId);
        }
    }

    @Override
    public void deleteReportById(Long reportId) throws ReportNotFoundException {
        Optional<Report> reportOptional = reportRepository.findById(reportId);
        if (reportOptional.isPresent()) {
            reportRepository.deleteById(reportId);
        } else {
            throw new ReportNotFoundException("Report not found with id: " + reportId);
        }
    }
}
