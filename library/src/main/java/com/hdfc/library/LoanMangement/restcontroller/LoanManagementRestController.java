package com.hdfc.library.LoanMangement.restcontroller;

import java.util.List;

// import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hdfc.library.Book.exception.BookNotFoundException;
import com.hdfc.library.Borrowing.exception.BorrowingNotFoundException;
import com.hdfc.library.LoanMangement.dto.LoanManagementDTO;
import com.hdfc.library.LoanMangement.exception.LoanNotFoundException;
import com.hdfc.library.LoanMangement.service.LoanServiceImp;
import com.hdfc.library.User.exception.UserNotFoundException;

@RestController
@RequestMapping("/api/v2/loanManagement")
public class LoanManagementRestController {
    @Autowired
    private LoanServiceImp loanServiceImp;

    @GetMapping("/getLoanById/{loanId}")
    public LoanManagementDTO getLoanById(@PathVariable Long loanId)
            throws LoanNotFoundException, UserNotFoundException, BookNotFoundException {
        return loanServiceImp.getLoanById(loanId);
    }

    @GetMapping("/getOverdueLoans")
    public List<LoanManagementDTO> getOverdueLoans()
            throws LoanNotFoundException, UserNotFoundException, BookNotFoundException {
        return loanServiceImp.getOverdueLoans();
    }

    @GetMapping("/remind/{loanId}")
    public ResponseEntity<String> sendReminder(@PathVariable Long loanId) {
        try {
            String reminderMessage = loanServiceImp.sendReminder(loanId);
            return ResponseEntity.ok().body(reminderMessage);
        } catch (LoanNotFoundException | BorrowingNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
