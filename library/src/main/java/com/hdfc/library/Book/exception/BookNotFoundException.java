package com.hdfc.library.Book.exception;

public class BookNotFoundException extends Exception {

    public BookNotFoundException() {
        super("Book not Found.");
    }

    public BookNotFoundException(String message) {
        super(message);
    }
}
