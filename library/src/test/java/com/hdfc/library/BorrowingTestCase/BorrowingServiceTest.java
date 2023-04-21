package com.hdfc.library.BorrowingTestCase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hdfc.library.Book.dto.BookDTO;
import com.hdfc.library.Book.exception.BookNotFoundException;
import com.hdfc.library.Book.service.BookServiceImp;
import com.hdfc.library.Borrowing.dto.BorrowingDTO;
import com.hdfc.library.Borrowing.exception.BookNotAvailableException;
import com.hdfc.library.Borrowing.exception.BorrowingIdAlreadyExistsException;
import com.hdfc.library.Borrowing.exception.BorrowingNotFoundException;
import com.hdfc.library.Borrowing.service.BorrowingServiceImp;
import com.hdfc.library.User.dto.UserDTO;
import com.hdfc.library.User.exception.UserNotActiveException;
import com.hdfc.library.User.exception.UserNotFoundException;
import com.hdfc.library.User.service.UserServiceImp;

@SpringBootTest
public class BorrowingServiceTest {

    @Autowired
    private UserServiceImp userService;

    @Autowired
    private BookServiceImp bookService;

    @Autowired
    private BorrowingServiceImp borrowingService;

    @Test
    public void testAddBorrowing() throws BorrowingIdAlreadyExistsException, UserNotFoundException,
            BookNotFoundException, BookNotAvailableException, UserNotActiveException {
        // Create a user
        UserDTO user = new UserDTO();
        user.setUserId(12L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("johndoe@example.com");
        user.setPassword("password");
        user.setAccountStatus("active");
        userService.addUser(user);

        // Create a book
        BookDTO book = new BookDTO();
        book.setBookId(12L);
        book.setTitle("The Great Gatsby");
        book.setAuthor("F. Scott Fitzgerald");
        book.setSubject("Fiction");
        book.setIsbn("978-3-16-148410-0");
        book.setPublisher("Scribner");
        book.setPublicationDate(LocalDate.of(1925, 4, 10));
        book.setQuantity(5);
        book.setAvailableQuantity(5);
        bookService.createBook(book);

        // Add a borrowing
        BorrowingDTO borrowingDTO = borrowingService.addBorrowing(9L, 12L, 12L, 1);
        assertNotNull(borrowingDTO);
        assertEquals(9L, borrowingDTO.getBorrowingId());
        assertEquals(12L, borrowingDTO.getUserId());
        assertEquals(12L, borrowingDTO.getBookId());
        assertEquals(1, borrowingDTO.getBookTaken());
    }

    @AfterEach
    public void testGetAllBorrow() throws BorrowingNotFoundException, UserNotFoundException, BookNotFoundException,
            BookNotAvailableException, UserNotActiveException, BorrowingIdAlreadyExistsException {
        BorrowingDTO borrows = borrowingService.getAllByBorrowId(9L);
        assertEquals(9L, borrows.getBorrowingId());
    }
}
