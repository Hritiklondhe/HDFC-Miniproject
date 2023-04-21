package com.hdfc.library.LoanMangement.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hdfc.library.LoanMangement.entity.LoanManagement;

public interface LoanRepository extends JpaRepository<LoanManagement, Long> {

    public List<LoanManagement> findByDueDateBefore(LocalDate date);

    @Query("select l from LoanManagement l where l.userFine > 0")
    public List<LoanManagement> findByFine();
}
