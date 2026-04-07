package com.choonsik.korail.service;

import com.choonsik.korail.entity.Enum;
import com.choonsik.korail.entity.Reservation;
import com.choonsik.korail.entity.Seat;
import com.choonsik.korail.entity.User;
import com.choonsik.korail.repository.ReservationRepository;
import com.choonsik.korail.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Transactional
    public boolean processPayment(List<Long> seatIdList, String paymentMethod, String amount, User user) {
        List<Seat> seats = seatRepository.findAllById(seatIdList);

        for (Seat seat : seats) {
            seat.setStatus(Enum.SeatStatus.PAID);

            Reservation reservation = new Reservation();
            reservation.setUser(user);
            reservation.setTrain(seat.getTrain());
            reservation.setSeat(seat);
            reservation.setReservationDate(LocalDateTime.now());
            reservation.setStatus(Enum.ReservationStatus.COMPLETED);
            reservationRepository.save(reservation);
        }

        seatRepository.saveAll(seats);
        return true;
    }

}