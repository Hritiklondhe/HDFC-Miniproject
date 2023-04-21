package com.hdfc.library.Reservation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

import com.hdfc.library.Reservation.entity.Reservation;

@RestController
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByBookIdAndCancellationDateIsNull(Long bookId);

}
