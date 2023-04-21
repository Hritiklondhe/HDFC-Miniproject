package com.hdfc.library.Borrowing.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hdfc.library.Book.exception.BookNotFoundException;
import com.hdfc.library.Borrowing.dto.BorrowingDTO;
import com.hdfc.library.Borrowing.exception.BookNotAvailableException;
import com.hdfc.library.Borrowing.exception.BorrowingIdAlreadyExistsException;
import com.hdfc.library.Borrowing.exception.BorrowingNotFoundException;
import com.hdfc.library.Borrowing.service.BorrowingServiceImp;
import com.hdfc.library.LoanMangement.exception.LoanNotFoundException;
import com.hdfc.library.Report.exception.ReportNotFoundException;
import com.hdfc.library.User.exception.UserNotActiveException;
import com.hdfc.library.User.exception.UserNotFoundException;

@RestController
@RequestMapping("/api/v2/borrowing")
public class BorrowingRestController {

    @Autowired
    private BorrowingServiceImp borrowingService;

    @PostMapping("/addBorrowing/{borrowingId}/{userId}/{bookId}/{bookTaken}")
    public BorrowingDTO addBorrowing(@PathVariable Long borrowingId, @PathVariable Long userId,
            @PathVariable Long bookId, @PathVariable Integer bookTaken)
            throws UserNotFoundException, BookNotFoundException, BookNotAvailableException, UserNotActiveException,
            BorrowingIdAlreadyExistsException {
        return borrowingService.addBorrowing(borrowingId, userId, bookId, bookTaken);
    }

    @PutMapping("/returnbook/{borrowingId}/{userId}/{bookId}/{bookReturn}")
    public BorrowingDTO returnBook(@PathVariable Long borrowingId, @PathVariable Long userId, @PathVariable Long bookId,
            @PathVariable Integer bookReturn)
            throws BorrowingNotFoundException, BookNotFoundException, UserNotFoundException, LoanNotFoundException,
            ReportNotFoundException {
        return borrowingService.returnBook(borrowingId, userId, bookId, bookReturn);
    }

    @GetMapping("/getAllByborrowBookId/{borrowingId}")
    public BorrowingDTO getAllByBorrowId(Long borrowingId) throws BorrowingNotFoundException {
        return borrowingService.getAllByBorrowId(borrowingId);
    }

}
