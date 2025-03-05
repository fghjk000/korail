package com.choonsik.korail.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stationId;

    @Column(nullable = false, unique = true)
    private String stationName;

    private String stationCode;
    private String location;
}
