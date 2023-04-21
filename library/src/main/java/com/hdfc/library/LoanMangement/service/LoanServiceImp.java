package com.hdfc.library.LoanMangement.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hdfc.library.Borrowing.entity.Borrowing;
import com.hdfc.library.Borrowing.exception.BorrowingNotFoundException;
import com.hdfc.library.Borrowing.repository.BorrowingRepository;
import com.hdfc.library.LoanMangement.dto.LoanManagementDTO;
import com.hdfc.library.LoanMangement.entity.LoanManagement;
import com.hdfc.library.LoanMangement.exception.LoanNotFoundException;
import com.hdfc.library.LoanMangement.repository.LoanRepository;

@Service
public class LoanServiceImp implements ILoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BorrowingRepository borrowingRepository;

    @Override
    public LoanManagementDTO getLoanById(Long loanId) throws LoanNotFoundException {
        Optional<LoanManagement> loanOptional = loanRepository.findById(loanId);
        if (loanOptional.isPresent()) {
            LoanManagement loanManagement = loanOptional.get();
            return new LoanManagementDTO(loanManagement.getLoanId(),
                    loanManagement.getUserId().getUserId(),
                    loanManagement.getBookId().getBookId(),
                    loanManagement.getDueDate(),
                    loanManagement.getUserFine());
        } else {
            throw new LoanNotFoundException("LoanId not found" + loanId);
        }
    }

    @Override
    public List<LoanManagementDTO> getOverdueLoans() {
        List<LoanManagementDTO> overdueLoans = new ArrayList<>();
        List<LoanManagement> loans = loanRepository.findByFine();
        for (LoanManagement loan : loans) {
            overdueLoans.add(new LoanManagementDTO(loan.getLoanId(),
                    loan.getUserId().getUserId(),
                    loan.getBookId().getBookId(),
                    loan.getDueDate(),
                    loan.getUserFine()));
        }
        return overdueLoans;
    }

    @Override
    public String sendReminder(Long loanId) throws LoanNotFoundException, BorrowingNotFoundException {
        Optional<Borrowing> optionalBorrowing = borrowingRepository.findById(loanId);
        Optional<LoanManagement> optionalLoan = loanRepository.findById(loanId);
        if (optionalBorrowing.isPresent() && optionalLoan.isPresent()) {
            Borrowing borrowing = optionalBorrowing.get();
            LoanManagement loan = optionalLoan.get();
            if (borrowing.getBorrowstatus().equals("RETURNED") && loan.getUserFine() > 0) {
                return "The book has already been returned you have to pay the loan amount " + loan.getUserFine();
            }
            if (borrowing.getBorrowstatus().equals("RETURNED") && loan.getUserFine() == 0) {
                return "The book has already been returned you don't have loan amount";
            }
            if(borrowing.getBorrowstatus().equals("BORROWED") && borrowing.getDueDate().compareTo(borrowing.getBorrowDate()) > 0){
                return "Due Date was " + borrowing.getDueDate() + ", please return the book to reduce the loan amount";
            }
            LocalDate dueDate = borrowing.getDueDate();
            if (dueDate.compareTo(LocalDate.now()) < 2 && dueDate.compareTo(LocalDate.now()) > 0) {
                return "Hurry and return your book before the due date.";
            }
            if (borrowing.getDueDate().compareTo(LocalDate.now().plusDays(2)) < 1
                    && borrowing.getDueDate().compareTo(LocalDate.now()) >= 0
                    && borrowing.getBorrowstatus().equals("BORROWED")) {
                return "Hurry up! Only 2 days left to return the book.";
            }
            if (borrowing.getDueDate().isEqual(LocalDate.now()) && borrowing.getBorrowstatus().equals("BORROWED")) {
                return "Today is your last day to return the book!";
            }
            return "The due date is " + dueDate + ", please return on time.";
        }
        throw new LoanNotFoundException("Loan with ID " + loanId + " not found");
    }

}
