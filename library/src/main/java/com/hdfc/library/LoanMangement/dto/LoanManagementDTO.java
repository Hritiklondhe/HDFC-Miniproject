package com.hdfc.library.LoanMangement.dto;

import java.time.LocalDate;

import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoanManagementDTO {

    @Id
    private Long loanId;

    private Long userId;

    private Long bookId;

    private LocalDate dueDate;

    private Long userFine;

}