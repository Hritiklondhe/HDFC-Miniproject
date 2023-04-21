package com.hdfc.library.Report.service;

import java.util.List;

import com.hdfc.library.Report.entity.Report;
import com.hdfc.library.Report.exception.ReportNotFoundException;

public interface IReportService {

    public List<Report> getAllReports();

    public Report getReportById(Long reportId) throws ReportNotFoundException;

    public void deleteReportById(Long reportId) throws ReportNotFoundException;

}
