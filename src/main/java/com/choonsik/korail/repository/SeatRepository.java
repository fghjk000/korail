package com.choonsik.korail.repository;

import com.choonsik.korail.entity.Enum;
import com.choonsik.korail.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findByTrain_TrainIdAndStatus(Long trainId, Enum.SeatStatus status);

    Seat findBySeatIdAndTrain_TrainId(Long seatId, Long trainId);

    List<Seat> findBySeatIdIn(Collection<Long> seatId);
}
