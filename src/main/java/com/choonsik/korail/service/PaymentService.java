package com.choonsik.korail.service;

import com.choonsik.korail.entity.Enum;
import com.choonsik.korail.entity.Seat;
import com.choonsik.korail.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

        @Autowired
        private SeatService seatService;

        public boolean processPayment(List<Long> seatIdList, String paymentMethod, String amount) {
            // 결제 로직 (가정: 결제 성공)
            boolean paymentSuccess = true;

            // 결제 성공 시 좌석 상태 업데이트
            if (paymentSuccess) {
                // 결제 후 좌석 상태를 "RESERVED"로 변경
                seatService.updateSeatStatusToPaid(seatIdList, "PAID");
            }

            return paymentSuccess;
        }

}