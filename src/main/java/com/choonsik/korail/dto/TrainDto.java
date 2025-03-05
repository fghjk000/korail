package com.choonsik.korail.dto;

import com.choonsik.korail.entity.Train;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class TrainDto {

    @NotEmpty(message = "기차 번호를 입력 해주세여")
    private String trainNumber;

    @NotEmpty(message = "기차 유형")
    private String trainType;

    @NotEmpty(message = "출발역을 입력해 주세요")
    private String departureStation;

    @NotEmpty(message = "도착역을 입력해 주세요")
    private String arrivalStation;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    private Integer availableSeats = 30; // 남은 좌석 필드 추가

    public static TrainDto fromEntity(Train train) {
        return new TrainDto(
                train.getTrainNumber(),
                train.getTrainType(),
                train.getDepartureStation(),
                train.getArrivalStation(),
                train.getDepartureTime(),
                train.getArrivalTime(),
                train.getAvailableSeats()
        );
    }

}
