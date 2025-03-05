package com.choonsik.korail.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @OneToOne
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    private LocalDateTime paymentDate;
    private Double amount;

    @Enumerated(EnumType.STRING)
    private Enum.PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private Enum.PaymentStatus status;
}
