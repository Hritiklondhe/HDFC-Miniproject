package com.hdfc.library.Report.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reports")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report {

    @Id
    @Column(name = "report_id")
    private Long reportId;

    @Column(name = "user_activity")
    private String userActivity;

    @Column(name = "book_status")
    private String bookStatus;

    @Column(name = "total_fine")
    private Long totalfine;

    @Column(name = "fines_collected")
    private String finesCollected;

}
