package com.hdfc.library.Book.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    private Long bookId;

    private String title;

    private String author;

    private String subject;

    private String isbn;

    private String publisher;

    private LocalDate publicationDate;

    private Integer quantity;

    private Integer availableQuantity;

}
