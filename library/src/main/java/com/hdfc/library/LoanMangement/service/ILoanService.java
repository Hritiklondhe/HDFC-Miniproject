package com.hdfc.library.LoanMangement.service;

import java.util.List;

import com.hdfc.library.Book.exception.BookNotFoundException;
import com.hdfc.library.Borrowing.exception.BorrowingNotFoundException;
import com.hdfc.library.LoanMangement.dto.LoanManagementDTO;
import com.hdfc.library.LoanMangement.exception.LoanNotFoundException;
import com.hdfc.library.User.exception.UserNotFoundException;

public interface ILoanService {

        public LoanManagementDTO getLoanById(Long LoanId)
                        throws LoanNotFoundException, UserNotFoundException, BookNotFoundException;

        public List<LoanManagementDTO> getOverdueLoans();

        public String sendReminder(Long loanId) throws LoanNotFoundException, BorrowingNotFoundException;

}
