package com.hdfc.library.Borrowing.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowingDTO {

    private Long borrowingId;

    private Long userId;

    private Long bookId;

    private Integer bookTaken;

    private LocalDate borrowDate;

    private LocalDate dueDate;

    private LocalDate returnDate;

    private String Borrowstatus;

}
