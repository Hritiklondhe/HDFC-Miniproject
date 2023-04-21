package com.hdfc.library.Borrowing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hdfc.library.Borrowing.entity.Borrowing;

public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {

}
