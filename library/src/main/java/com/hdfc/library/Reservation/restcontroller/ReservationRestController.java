package com.hdfc.library.Reservation.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hdfc.library.Book.exception.BookNotFoundException;
import com.hdfc.library.Reservation.dto.ReservationDTO;
import com.hdfc.library.Reservation.exception.BookAlreadyReservedException;
import com.hdfc.library.Reservation.exception.ReservationNotFoundException;
import com.hdfc.library.Reservation.service.ReservationServiceImp;
import com.hdfc.library.User.exception.UserNotFoundException;

@RestController
@RequestMapping("/api/v2/reservation")
public class ReservationRestController {

    @Autowired
    private ReservationServiceImp reservationServiceImp;

    @PostMapping("/add/{reservationId}/{userId}/{bookId}")
    public ReservationDTO addReservation(@PathVariable Long reservationId, @PathVariable Long userId,
            @PathVariable Long bookId)
            throws UserNotFoundException, BookNotFoundException, BookAlreadyReservedException {
        return reservationServiceImp.addReservation(reservationId, userId, bookId);

    }

    @PutMapping("/cancelreservation/{reservationId}/{userId}/{bookId}")
    public ReservationDTO cancelReservation(@PathVariable Long reservationId, @PathVariable Long userId,
            @PathVariable Long bookId)
            throws ReservationNotFoundException, UserNotFoundException,
            BookNotFoundException {
        return reservationServiceImp.cancelReservation(reservationId, userId, bookId);
    }

    @GetMapping("/getReservation/{reservationId}")
    public ReservationDTO getReservation(@PathVariable Long reservationId) throws ReservationNotFoundException {
        return reservationServiceImp.getReservation(reservationId);
    }

}
