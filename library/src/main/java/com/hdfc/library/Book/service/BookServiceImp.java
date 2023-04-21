package com.hdfc.library.Book.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hdfc.library.Book.dto.BookDTO;
import com.hdfc.library.Book.entity.Book;
import com.hdfc.library.Book.exception.BookNotFoundException;
import com.hdfc.library.Book.repository.BookRepository;

@Service
public class BookServiceImp implements IBookService {

    private final BookRepository bookRepository;

    private BookServiceImp(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(book -> new Book(book.getBookId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getSubject(),
                        book.getIsbn(),
                        book.getPublisher(),
                        book.getPublicationDate(),
                        book.getQuantity(),
                        book.getAvailableQuantity()))
                .collect(Collectors.toList());
    }

    @Override
    public Book getBookById(Long bookId) throws BookNotFoundException {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            return new Book(book.getBookId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getSubject(),
                    book.getIsbn(),
                    book.getPublisher(),
                    book.getPublicationDate(),
                    book.getQuantity(),
                    book.getAvailableQuantity());
        } else {
            throw new BookNotFoundException("Book not found with id: " + bookId);
        }
    }

    @Override
    public Book createBook(BookDTO bookDTO) {
        Book book = new Book(bookDTO.getBookId(),
                bookDTO.getTitle(),
                bookDTO.getAuthor(),
                bookDTO.getSubject(),
                bookDTO.getIsbn(),
                bookDTO.getPublisher(),
                bookDTO.getPublicationDate(),
                bookDTO.getQuantity(),
                bookDTO.getAvailableQuantity());
        return bookRepository.save(book);

    }

    @Override
    public Book updateBook(Long bookId, BookDTO bookDTO) throws BookNotFoundException {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            book.setBookId(bookDTO.getBookId());
            book.setTitle(bookDTO.getTitle());
            book.setAuthor(bookDTO.getAuthor());
            book.setSubject(bookDTO.getSubject());
            book.setIsbn(bookDTO.getIsbn());
            book.setPublicationDate(bookDTO.getPublicationDate());
            book.setQuantity(bookDTO.getQuantity());
            book.setAvailableQuantity(bookDTO.getAvailableQuantity());
            return bookRepository.save(book);
        } else {
            throw new BookNotFoundException();
        }

    }

    @Override
    public void deleteBookById(Long bookId) throws BookNotFoundException {
        if (bookRepository.existsById(bookId)) {
            bookRepository.deleteById(bookId);
        } else {
            throw new BookNotFoundException("Book not found with id " + bookId);
        }
    }

    @Override
    public void deleteBook(BookDTO bookDTO) throws BookNotFoundException {
        Optional<Book> optionalBook = bookRepository.findById(bookDTO.getBookId());
        if (optionalBook.isPresent()) {
            bookRepository.delete(optionalBook.get());
        } else {
            throw new BookNotFoundException();
        }
    }

    @Override
    public List<Book> searchByTitle(String title) throws BookNotFoundException {
        List<Book> books = bookRepository.findByTitle(title);
        if (books.isEmpty()) {
            throw new BookNotFoundException("No books found with title: " + title);
        }
        return books;
    }

    @Override
    public List<Book> searchByAuthor(String author) throws BookNotFoundException {
        List<Book> books = bookRepository.findByAuthor(author);
        if (books.isEmpty()) {
            throw new BookNotFoundException("No books found with author: " + author);
        }
        return books;
    }

    @Override
    public List<Book> searchBySubject(String subject) throws BookNotFoundException {
        List<Book> books = bookRepository.findBySubject(subject);
        if (books.isEmpty()) {
            throw new BookNotFoundException("No books found with : " + subject);
        }
        return books;
    }

    @Override
    public boolean isBookAvailable(Long bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        boolean res = (bookOptional.isPresent()) ? true : false;
        return res;
    }

}
