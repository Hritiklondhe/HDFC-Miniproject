package com.hdfc.library.Report.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hdfc.library.Report.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {

}
