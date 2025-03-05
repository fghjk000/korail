package com.choonsik.korail.service;

import com.choonsik.korail.entity.Enum;
import com.choonsik.korail.entity.Seat;
import com.choonsik.korail.entity.Train;
import com.choonsik.korail.repository.SeatRepository;
import com.choonsik.korail.entity.Enum.SeatStatus;
import com.choonsik.korail.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private TrainRepository trainRepository;

    // 좌석 조회: 기차 ID에 해당하는 예약 가능한 좌석 목록 조회
    public List<Seat> getAvailableSeats(Long trainId) {
        return seatRepository.findByTrain_TrainIdAndStatus(trainId, SeatStatus.AVAILABLE);
    }

    // 좌석 예약 처리
    public boolean reserveSeats(Long trainId, List<Long> selectedSeatIds) {
        boolean success = true;

        for (Long seatId : selectedSeatIds) {
            Seat seat = seatRepository.findBySeatIdAndTrain_TrainId(seatId, trainId);

            if (seat == null || seat.getStatus() != SeatStatus.AVAILABLE) {
                success = false;
                break;
            }

            seat.setStatus(SeatStatus.RESERVED); // 좌석 상태를 예약으로 변경
            seatRepository.save(seat); // 변경된 좌석 정보 저장

            Train train = seat.getTrain(); // 좌석이 속한 트레인 정보 가져오기
            if (train != null) {
                train.setAvailableSeats(train.getAvailableSeats() - 1); // available_seats 감소
                trainRepository.save(train); // 변경된 트레인 정보 저장
            }

        }

        return success;
    }

    public List<Seat> findSeatsByIds(List<Long> seatIds) {
        return seatRepository.findBySeatIdIn(seatIds);  // seatIdIn 메소드가 List<Long>을 받도록
    }


    public void updateSeatStatusToPaid(List<Long> seatIdList, String status) {
        // 좌석 ID 목록을 기반으로 좌석을 찾아 상태를 업데이트
        List<Seat> seats = seatRepository.findAllById(seatIdList);

        for (Seat seat : seats) {
            // 좌석 상태를 "RESERVED"로 변경
            seat.setStatus(SeatStatus.valueOf(status));
        }

        // 변경된 상태를 데이터베이스에 반영
        seatRepository.saveAll(seats);
    }

}
