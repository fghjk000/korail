package com.choonsik.korail.repository;

import com.choonsik.korail.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainRepository extends JpaRepository<Train, Long> {

    List<Train> findByDepartureStationAndArrivalStation(String departureStation, String arrivalStation);

    Train findByTrainNumber(String trainNumber);
}
