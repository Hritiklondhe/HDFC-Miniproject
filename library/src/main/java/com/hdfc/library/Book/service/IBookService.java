package com.hdfc.library.Book.service;

import java.util.List;

import com.hdfc.library.Book.dto.BookDTO;
import com.hdfc.library.Book.entity.Book;
import com.hdfc.library.Book.exception.BookNotFoundException;

public interface IBookService {

    public List<Book> getAllBooks();

    public Book getBookById(Long bookId) throws BookNotFoundException;

    public Book createBook(BookDTO bookDTO);

    public Book updateBook(Long bookId, BookDTO bookDTO) throws BookNotFoundException;

    public void deleteBook(BookDTO bookDTO) throws BookNotFoundException;

    public List<Book> searchByTitle(String title) throws BookNotFoundException;

    public List<Book> searchByAuthor(String author) throws BookNotFoundException;

    public List<Book> searchBySubject(String subject) throws BookNotFoundException;

    public void deleteBookById(Long bookId) throws BookNotFoundException;

    public boolean isBookAvailable(Long bookId);
}
