package com.hdfc.library.BookTestCase;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hdfc.library.Book.dto.BookDTO;
import com.hdfc.library.Book.entity.Book;
import com.hdfc.library.Book.exception.BookNotFoundException;
import com.hdfc.library.Book.service.IBookService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

@SpringBootTest
public class BookServiceTest {

    @Autowired
    private IBookService bookService;

    private BookDTO bookDto;

    @BeforeEach
    public void setUp() {
        bookDto = new BookDTO();
        bookDto.setBookId(11L);
        bookDto.setTitle("Test Book");
        bookDto.setAuthor("John Doe");
        bookDto.setSubject("Test Subject");
        bookDto.setIsbn("1234567890");
        bookDto.setPublisher("Brown and Commpany");
        bookDto.setPublicationDate(LocalDate.now());
        bookDto.setQuantity(10);
        bookDto.setAvailableQuantity(5);
    }

    @Test
    public void testGetAllBooks() {
        List<Book> books = bookService.getAllBooks();
        Assertions.assertNotNull(books);
    }

    @Test
    public void testGetBookById() throws BookNotFoundException {
        Book createdBook = bookService.createBook(bookDto);
        Book retrievedBook = bookService.getBookById(createdBook.getBookId());
        Assertions.assertEquals(createdBook.getBookId(), retrievedBook.getBookId());
    }

    @Test
    public void testCreateBook() {
        Book createdBook = bookService.createBook(bookDto);
        Assertions.assertNotNull(createdBook.getBookId());
    }

    @Test
    public void testUpdateBook() throws BookNotFoundException {
        Book createdBook = bookService.createBook(bookDto);
        BookDTO updatedBookDto = new BookDTO();
        updatedBookDto.setBookId(11L);
        updatedBookDto.setTitle("Updated Test Book");
        updatedBookDto.setAuthor("Jane Doe");
        updatedBookDto.setSubject("Updated Test Subject");
        updatedBookDto.setIsbn("0987654321");
        updatedBookDto.setPublisher("Brown and Commpany");
        updatedBookDto.setPublicationDate(LocalDate.now().minusDays(3));
        updatedBookDto.setQuantity(10);
        updatedBookDto.setAvailableQuantity(10);
        Book updatedBook = bookService.updateBook(createdBook.getBookId(), updatedBookDto);
        Assertions.assertEquals(updatedBook.getTitle(), updatedBookDto.getTitle());
        Assertions.assertEquals(updatedBook.getAuthor(), updatedBookDto.getAuthor());
        Assertions.assertEquals(updatedBook.getSubject(), updatedBookDto.getSubject());
        Assertions.assertEquals(updatedBook.getIsbn(), updatedBookDto.getIsbn());
        Assertions.assertEquals(updatedBookDto.getPublisher(), updatedBook.getPublisher());
        Assertions.assertEquals(updatedBookDto.getQuantity(), updatedBook.getQuantity());
        Assertions.assertEquals(updatedBook.getAvailableQuantity(), updatedBookDto.getAvailableQuantity());
    }

    @Test
    public void testDeleteBook() throws BookNotFoundException {
        Book createdBook = bookService.createBook(bookDto);
        BookDTO bookToDelete = new BookDTO();
        bookToDelete.setBookId(createdBook.getBookId());
        bookService.deleteBook(bookToDelete);
        Assertions.assertThrows(BookNotFoundException.class, () -> bookService.getBookById(createdBook.getBookId()));
    }

    @Test
    public void testSearchByTitle() throws BookNotFoundException {
        Book createdBook = bookService.createBook(bookDto);
        List<Book> books = bookService.searchByTitle(bookDto.getTitle());
        Assertions.assertFalse(books.isEmpty());
        Assertions.assertEquals(createdBook.getTitle(), books.get(0).getTitle());
    }

    @Test
    public void testSearchByAuthor() throws BookNotFoundException {
        Book createdBook = bookService.createBook(bookDto);
        List<Book> books = bookService.searchByAuthor(bookDto.getAuthor());
        Assertions.assertFalse(books.isEmpty());
        Assertions.assertEquals(createdBook.getAuthor(), books.get(0).getAuthor());
    }

    @Test
    public void testSearchBySubject() throws BookNotFoundException {
        Book createdBook = bookService.createBook(bookDto);
        List<Book> books = bookService.searchBySubject(bookDto.getSubject());
        Assertions.assertFalse(books.isEmpty());
        Assertions.assertEquals(createdBook.getSubject(), books.get(0).getSubject());
    }

}