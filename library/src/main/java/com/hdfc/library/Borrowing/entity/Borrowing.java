package com.hdfc.library.Borrowing.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.hdfc.library.Book.entity.Book;
import com.hdfc.library.User.entity.User;

@Entity
@Table(name = "Borrowing")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Borrowing {

    @Id
    @Column(name = "borrow_id")
    private Long borrowingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book bookId;

    @Column(name = "book_taken")
    private Integer bookTaken;

    @Column(name = "borrow_date")
    private LocalDate borrowDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(name = "status")
    private String Borrowstatus;

}