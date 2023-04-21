package com.hdfc.library.Reservation.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hdfc.library.Book.entity.Book;
import com.hdfc.library.Book.exception.BookNotFoundException;
import com.hdfc.library.Book.repository.BookRepository;
import com.hdfc.library.Reservation.dto.ReservationDTO;
import com.hdfc.library.Reservation.entity.Reservation;
import com.hdfc.library.Reservation.exception.BookAlreadyReservedException;
import com.hdfc.library.Reservation.exception.ReservationNotFoundException;
import com.hdfc.library.Reservation.repository.ReservationRepository;
import com.hdfc.library.User.entity.User;
import com.hdfc.library.User.exception.UserNotFoundException;
import com.hdfc.library.User.repository.UserRepository;

/*  The addReservation method takes in three parameters: reservationId, 
    userId, and bookId, and attempts to add a new reservation with the given 
    parameters. It throws three different exceptions: UserNotFoundException, 
    BookNotFoundException, and BookAlreadyReservedException.

    The cancelReservation method takes in the same three parameters as addReservation 
    and attempts to cancel the reservation with the given parameters. It throws three 
    different exceptions: ReservationNotFoundException, UserNotFoundException, and 
    BookNotFoundException.

    The getReservation method takes in a reservationId parameter and attempts to retrieve 
    the reservation with the given ID. It throws a ReservationNotFoundException exception 
    if the reservation is not found.

    All methods return a ReservationDTO object, which contains information about the reservation. */

@Service
public class ReservationServiceImp implements IReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ReservationDTO addReservation(Long reservationId, Long userId, Long bookId)
            throws UserNotFoundException, BookNotFoundException,
            BookAlreadyReservedException {

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with ID " + userId + " not found");
        }
        User user = optionalUser.get();

        // find book by ID
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()) {
            throw new BookNotFoundException("Book with ID " + bookId + " not found");
        }
        Book book = optionalBook.get();

        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);
        if (optionalBook.isPresent()) {
            throw new BookAlreadyReservedException("Reservation with ID " + reservationId + " have already reserved");
        }
        Reservation reservation = optionalReservation.get();

        // create new reservation
        reservation.setReservationId(reservationId);
        reservation.setUserId(user);
        reservation.setBookId(book);
        reservation.setReservationDate(LocalDate.now());
        reservation.setCancellationDate(null);

        // save reservation to database
        Reservation savedReservation = reservationRepository.save(reservation);

        // create and return DTO
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setReservationId(savedReservation.getReservationId());
        reservationDTO.setUserId(savedReservation.getUserId().getUserId());
        reservationDTO.setBookId(savedReservation.getBookId().getBookId());
        reservationDTO.setReservationDate(savedReservation.getReservationDate());
        reservationDTO.setCancellationDate(savedReservation.getCancellationDate());

        return reservationDTO;

    }

    @Override
    public ReservationDTO cancelReservation(Long reservationId, Long userId, Long bookId)
            throws ReservationNotFoundException, UserNotFoundException,
            BookNotFoundException {
        // find reservation by ID
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found with ID" + reservationId));

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with ID " + userId + " not found");
        }

        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()) {
            throw new BookNotFoundException("Book with ID " + bookId + " not found");
        }

        if (reservation.getUserId().getUserId().equals(userId)
                && reservation.getBookId().getBookId().equals(bookId)) {
            reservation.setCancellationDate(LocalDate.now());
            reservationRepository.save(reservation);
            return new ReservationDTO(reservation.getReservationId(),
                    reservation.getUserId().getUserId(),
                    reservation.getBookId().getBookId(),
                    reservation.getReservationDate(),
                    reservation.getCancellationDate());
        } else {
            throw new ReservationNotFoundException("Reservation not found with given userId and bookId");
        }
    }

    @Override
    public ReservationDTO getReservation(Long reservationId) throws ReservationNotFoundException {
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);
        if (reservationOptional.isPresent()) {
            Reservation reservation = reservationOptional.get();
            return new ReservationDTO(reservation.getReservationId(),
                    reservation.getUserId().getUserId(), reservation.getBookId().getBookId(),
                    reservation.getReservationDate(), reservation.getCancellationDate());
        } else {
            throw new ReservationNotFoundException("ReservationId " + reservationId + " not found");
        }
    }

}
