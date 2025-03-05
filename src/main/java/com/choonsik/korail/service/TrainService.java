package com.choonsik.korail.service;

import com.choonsik.korail.dto.TrainDto;
import com.choonsik.korail.entity.Train;
import com.choonsik.korail.repository.TrainRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainService {

    @Autowired
    private TrainRepository trainRepository;


    public TrainDto createTrain(TrainDto trainDto) {
        Train train = new Train();
        train.setTrainNumber(trainDto.getTrainNumber());
        train.setTrainType(trainDto.getTrainType());
        train.setDepartureStation(trainDto.getDepartureStation());
        train.setArrivalStation(trainDto.getArrivalStation());
        train.setDepartureTime(trainDto.getDepartureTime());
        train.setArrivalTime(trainDto.getArrivalTime());
        train.setAvailableSeats(30); // 기본값 30으로 설정

        Train createdTrain = trainRepository.save(train);
        return TrainDto.fromEntity(createdTrain);
    }

    public List<TrainDto> getTrainByStations(String departureStation, String arrivalStation) {
        List<Train> trains = trainRepository.findByDepartureStationAndArrivalStation(departureStation, arrivalStation);
        return trains.stream()
                .map(TrainDto::fromEntity)
                .collect(Collectors.toList());
    }


    public List<Train> getTrainList() {
        return trainRepository.findAll();
    }

    public Train findTrainByNumber(String trainNumber) {
        return trainRepository.findByTrainNumber(trainNumber);
    }

    public Train getTrainById(Long trainId) {
        // `findById` 메서드는 Optional을 반환하므로, 값이 있으면 반환하고, 없으면 예외를 던지거나 기본값을 반환할 수 있습니다.
        Optional<Train> train = trainRepository.findById(trainId);
        return train.orElseThrow(() -> new RuntimeException("Train not found with id: " + trainId));
    }
}
