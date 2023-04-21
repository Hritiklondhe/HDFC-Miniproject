package com.hdfc.library.LoanMangement.entity;

import com.hdfc.library.Book.entity.Book;
import com.hdfc.library.User.entity.User;

import lombok.Data;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "Loan")
public class LoanManagement {

    @Id
    @Column(name = "loan_id")
    private Long loanId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book bookId;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "user_fine")
    private Long userFine;

}
