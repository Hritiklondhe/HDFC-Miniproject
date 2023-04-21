package com.hdfc.library.Book.restcontroller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hdfc.library.Book.dto.BookDTO;
import com.hdfc.library.Book.entity.Book;
import com.hdfc.library.Book.exception.BookNotFoundException;
import com.hdfc.library.Book.service.BookServiceImp;

@RestController
@RequestMapping("/api/v2/book")
public class BookRestController {

    @Autowired
    private BookServiceImp bookService;

    public BookRestController(BookServiceImp bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/getall")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/getbookbyid/{bookId}")
    public Book getBookById(@PathVariable Long bookId) throws BookNotFoundException {
        return bookService.getBookById(bookId);
    }

    @PostMapping("/add")
    public Book createBook(@RequestBody BookDTO bookDTO) {
        return bookService.createBook(bookDTO);
    }

    @PutMapping("/update/{bookId}")
    public Book updateBook(@PathVariable Long bookId, @RequestBody BookDTO bookDTO) throws BookNotFoundException {
        return bookService.updateBook(bookId, bookDTO);
    }

    @DeleteMapping("/delete")
    public void deleteBook(@RequestBody BookDTO bookDTO) throws BookNotFoundException {
        bookService.deleteBook(bookDTO);
    }

    @GetMapping("/getBytitle/{title}")
    public List<Book> searchByTitle(@PathVariable String title) throws BookNotFoundException {
        return bookService.searchByTitle(title);
    }

    @GetMapping("/getByauthor/{author}")
    public List<Book> searchByAuthor(@PathVariable String author) throws BookNotFoundException {
        return bookService.searchByAuthor(author);
    }

    @GetMapping("/getBysubject/{subject}")
    public List<Book> searchBySubject(@PathVariable String subject) throws BookNotFoundException {
        return bookService.searchBySubject(subject);
    }

    @DeleteMapping("/deletebyid/{bookId}")
    public void deleteBookById(@PathVariable Long bookId) throws BookNotFoundException {
        bookService.deleteBookById(bookId);
    }

}
