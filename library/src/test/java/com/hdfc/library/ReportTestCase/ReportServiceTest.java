package com.hdfc.library.ReportTestCase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.hdfc.library.Report.entity.Report;

@SpringBootTest
public class ReportServiceTest {

    @Test
    public void testReportCreation() {
        Report report = new Report();
        assertNotNull(report);
    }

    @Test
    public void testReportGetById() {
        Report report = new Report();
        report.setReportId(1L);
        report.setUserActivity("ACTIVE");
        report.setBookStatus("BORROWED");
        report.setTotalfine(0L);
        report.setFinesCollected("NO PENDING");

        assertEquals(1L, report.getReportId());
        assertEquals("ACTIVE", report.getUserActivity());
        assertEquals("BORROWED", report.getBookStatus());
        assertEquals(0L, report.getTotalfine());
        assertEquals("NO PENDING", report.getFinesCollected());
    }

}
