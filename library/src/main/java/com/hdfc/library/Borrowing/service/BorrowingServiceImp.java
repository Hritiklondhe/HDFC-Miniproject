package com.hdfc.library.Borrowing.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hdfc.library.Book.entity.Book;
import com.hdfc.library.Book.exception.BookNotFoundException;
import com.hdfc.library.Book.repository.BookRepository;
import com.hdfc.library.Borrowing.dto.BorrowingDTO;
import com.hdfc.library.Borrowing.entity.Borrowing;
import com.hdfc.library.Borrowing.exception.BookNotAvailableException;
import com.hdfc.library.Borrowing.exception.BorrowingIdAlreadyExistsException;
import com.hdfc.library.Borrowing.exception.BorrowingNotFoundException;
import com.hdfc.library.Borrowing.repository.BorrowingRepository;
import com.hdfc.library.LoanMangement.entity.LoanManagement;
import com.hdfc.library.LoanMangement.exception.LoanNotFoundException;
import com.hdfc.library.LoanMangement.repository.LoanRepository;
import com.hdfc.library.Report.entity.Report;
import com.hdfc.library.Report.exception.ReportNotFoundException;
import com.hdfc.library.Report.repository.ReportRepository;
import com.hdfc.library.User.entity.User;
import com.hdfc.library.User.exception.UserNotActiveException;
import com.hdfc.library.User.exception.UserNotFoundException;
import com.hdfc.library.User.repository.UserRepository;

@Service
public class BorrowingServiceImp implements IBorrowingService {

    @Autowired
    private BorrowingRepository borrowingRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private LoanRepository loanRepository;

    public BorrowingDTO addBorrowing(Long borrowingId, Long userId, Long bookId, Integer bookTaken)
            throws UserNotFoundException, BookNotFoundException, BookNotAvailableException, UserNotActiveException,
            BorrowingIdAlreadyExistsException {

        // find user by ID
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with ID " + userId + " not found");
        }
        User user = optionalUser.get();

        // find book by ID
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()) {
            throw new BookNotFoundException("Book with ID " + bookId + " not found");
        }
        Book book = optionalBook.get();

        // check if book is available for borrowing
        if (book.getAvailableQuantity() < bookTaken) {
            throw new BookNotAvailableException(
                    "Not enough copies of book with ID " + bookId + " available");
        }

        // check if user is active or not
        if (user.getAccountStatus().equalsIgnoreCase("InActive")) {
            throw new UserNotActiveException("User is not active");
        }

        Optional<Borrowing> optionalBorrowing = borrowingRepository.findById(borrowingId);
        if (optionalBorrowing.isPresent()) {
            throw new BorrowingIdAlreadyExistsException("Borrowing Id " + borrowingId + " already exists");
        }
        Borrowing borrowing = new Borrowing();

        // update book availability
        book.setAvailableQuantity(book.getAvailableQuantity() - bookTaken);
        bookRepository.save(book);

        // create borrowing entity
        borrowing.setBorrowingId(borrowingId);
        borrowing.setUserId(user);
        borrowing.setBookId(book);
        borrowing.setBookTaken(bookTaken);
        borrowing.setBorrowDate(LocalDate.now());
        borrowing.setDueDate(LocalDate.now().plusDays(1));
        borrowing.setReturnDate(null);
        borrowing.setBorrowstatus("BORROWED");

        // save borrowing entity
        borrowingRepository.save(borrowing);

        // save in loan entity
        LoanManagement loan = new LoanManagement();
        loan.setLoanId(borrowingId);
        loan.setUserId(user);
        loan.setBookId(book);
        loan.setDueDate(borrowing.getDueDate());
        loan.setUserFine(0l);

        loanRepository.save(loan);

        // save in reports
        Report report = new Report();
        report.setReportId(borrowingId);
        report.setUserActivity(user.getAccountStatus());
        report.setBookStatus(borrowing.getBorrowstatus());
        report.setTotalfine(null);
        report.setFinesCollected(null);

        reportRepository.save(report);

        // create and return borrowing DTO
        BorrowingDTO borrowingDTO = new BorrowingDTO();
        borrowingDTO.setBorrowingId(borrowingId);
        borrowingDTO.setUserId(userId);
        borrowingDTO.setBookId(bookId);
        borrowingDTO.setBookTaken(bookTaken);
        borrowingDTO.setBorrowDate(borrowing.getBorrowDate());
        borrowingDTO.setDueDate(borrowing.getDueDate());
        borrowingDTO.setReturnDate(borrowing.getReturnDate());
        borrowingDTO.setBorrowstatus(borrowing.getBorrowstatus());
        return borrowingDTO;
    }

    @Override
    public BorrowingDTO getAllByBorrowId(Long borrowingId) throws BorrowingNotFoundException {
        Optional<Borrowing> borrowsOptional = borrowingRepository.findById(borrowingId);
        if (borrowsOptional.isPresent()) {
            Borrowing borrowing = borrowsOptional.get();
            return new BorrowingDTO(borrowing.getBorrowingId(), borrowing.getUserId().getUserId(),
                    borrowing.getBookId().getBookId(), borrowing.getBookTaken(),
                    borrowing.getBorrowDate(),
                    borrowing.getDueDate(), borrowing.getReturnDate(), borrowing.getBorrowstatus());

        } else {
            throw new BorrowingNotFoundException("Borrowing Id " + borrowingId + " not found");
        }
    }

    @Override
    public BorrowingDTO returnBook(Long borrowingId, Long userId, Long bookId, Integer bookReturn)
            throws BorrowingNotFoundException, BookNotFoundException, UserNotFoundException, LoanNotFoundException,
            ReportNotFoundException {

        // Get the Borrowing object using borrowingId
        Borrowing borrowing = borrowingRepository.findById(borrowingId)
                .orElseThrow(() -> new BorrowingNotFoundException(
                        "Borrowing not found with ID: " + borrowingId));

        // Get the LoanManagement object using borrowingId
        LoanManagement loan = loanRepository.findById(borrowingId)
                .orElseThrow(() -> new LoanNotFoundException("Loan not found with Id:" + borrowingId));

        Report report = reportRepository.findById(borrowingId)
                .orElseThrow(() -> new ReportNotFoundException("Report not found with Id:" + borrowingId));

        // Get the User object using userId
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with ID " + userId + " not found");
        }
        User user = optionalUser.get();

        // Get the Book object using bookId
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()) {
            throw new BookNotFoundException("Book with ID " + bookId + " not found");
        }
        Book book = optionalBook.get();

        // Check if the borrowing is associated with the correct user and book
        if (borrowing.getUserId().equals(user) && borrowing.getBookId().equals(book)) {

            // Set the return date, borrow status, and update available quantity
            borrowing.setReturnDate(LocalDate.now());
            borrowing.setBorrowstatus("RETURNED");
            borrowing.getBookId().setAvailableQuantity(borrowing.getBookId().getAvailableQuantity() + bookReturn);
            borrowingRepository.save(borrowing);

            // Calculate the fine for loan if applicable
            if (loan.getLoanId().equals(borrowingId)) {
                if (borrowing.getReturnDate().compareTo(borrowing.getDueDate()) > 0) {
                    int delayDays = (int) ChronoUnit.DAYS.between(borrowing.getDueDate(), borrowing.getReturnDate());
                    long fine = delayDays * 50;
                    loan.setUserFine(fine);
                } else {
                    loan.setUserFine(0l);
                }
                loanRepository.save(loan);
            }

            if (report.getReportId().equals(borrowingId)) {
                if (borrowing.getReturnDate().compareTo(borrowing.getDueDate()) > 0) {
                    int delayDays = (int) ChronoUnit.DAYS.between(borrowing.getDueDate(), borrowing.getReturnDate());
                    long fine = delayDays * 50;
                    report.setTotalfine(fine);
                    report.setBookStatus(borrowing.getBorrowstatus());
                    report.setFinesCollected("PENDING");
                } else {
                    report.setTotalfine(0l);
                    report.setBookStatus(borrowing.getBorrowstatus());
                    report.setFinesCollected("NO PENDING");
                }
                loanRepository.save(loan);
            }

            // Return the updated BorrowingDTO object
            return new BorrowingDTO(borrowing.getBorrowingId(), borrowing.getUserId().getUserId(),
                    borrowing.getBookId().getBookId(), borrowing.getBookTaken(), borrowing.getBorrowDate(),
                    borrowing.getDueDate(), borrowing.getReturnDate(), borrowing.getBorrowstatus());

        } else {
            throw new BorrowingNotFoundException("Borrowing not found with given user ID and book ID");
        }
    }

}
