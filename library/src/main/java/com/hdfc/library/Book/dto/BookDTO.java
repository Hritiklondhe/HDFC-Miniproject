package com.hdfc.library.Book.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

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
