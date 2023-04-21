package com.hdfc.library.Borrowing.service;

import com.hdfc.library.Book.exception.BookNotFoundException;
import com.hdfc.library.Borrowing.dto.BorrowingDTO;
import com.hdfc.library.Borrowing.exception.BookNotAvailableException;
import com.hdfc.library.Borrowing.exception.BorrowingIdAlreadyExistsException;
import com.hdfc.library.Borrowing.exception.BorrowingNotFoundException;
import com.hdfc.library.LoanMangement.exception.LoanNotFoundException;
import com.hdfc.library.Report.exception.ReportNotFoundException;
import com.hdfc.library.User.exception.UserNotActiveException;
import com.hdfc.library.User.exception.UserNotFoundException;

public interface IBorrowingService {

        public BorrowingDTO addBorrowing(Long borrowingId, Long userId, Long bookId, Integer bookTaken)
                        throws UserNotFoundException, BookNotFoundException, BookNotAvailableException,
                        UserNotActiveException, BorrowingIdAlreadyExistsException;

        public BorrowingDTO returnBook(Long borrowingId, Long userId, Long bookId, Integer bookReturn)
                        throws BorrowingNotFoundException, BookNotFoundException, UserNotFoundException,
                        LoanNotFoundException, ReportNotFoundException;

        public BorrowingDTO getAllByBorrowId(Long borrowingId) throws BorrowingNotFoundException;

}
