package com.hdfc.library.Reservation.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {

    private Long reservationId;

    private Long userId;

    private Long bookId;

    private LocalDate reservationDate;

    private LocalDate cancellationDate;

}
