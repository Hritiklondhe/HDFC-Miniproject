package com.hdfc.library.Report.dto;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReportDTO {

    @Id
    private Long reportId;

    private String userActivity;

    private String bookStatus;

    private Long totalfine;

    private String finesCollected;
}
