package com.hdfc.library.Reservation.service;

import com.hdfc.library.Book.exception.BookNotFoundException;
import com.hdfc.library.Reservation.dto.ReservationDTO;
import com.hdfc.library.Reservation.exception.BookAlreadyReservedException;
import com.hdfc.library.Reservation.exception.ReservationNotFoundException;
import com.hdfc.library.User.exception.UserNotFoundException;

public interface IReservationService {

    public ReservationDTO addReservation(Long reservationId, Long userId, Long bookId)
            throws UserNotFoundException, BookNotFoundException,
            BookAlreadyReservedException;

    public ReservationDTO cancelReservation(Long reservationId, Long userId, Long bookId)
            throws ReservationNotFoundException, UserNotFoundException,
            BookNotFoundException;

    public ReservationDTO getReservation(Long reservationId) throws ReservationNotFoundException;

}
