package com.choonsik.korail.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trainId;

    @Column(nullable = false, unique = true)
    private String trainNumber;

    private String trainType;

    private String departureStation;

    private String arrivalStation;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;


    @Column(nullable = false)
    private Integer availableSeats = 30;

    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats = new ArrayList<>();

    public Train() {
        // 30개의 좌석을 자동으로 추가하는 로직
        for (int i = 1; i <= availableSeats; i++) {
            Seat seat = new Seat();
            seat.setSeatNumber("S" + i);
            seat.setTrain(this);  // Train 객체와 연결
            seat.setStatus(Enum.SeatStatus.AVAILABLE);  // 초기 상태는 AVAILABLE
            seats.add(seat);
        }
    }

}
