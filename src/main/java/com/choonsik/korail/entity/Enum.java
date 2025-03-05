package com.choonsik.korail.entity;

public class Enum {

    public enum UserRole {
        ADMIN, USER
    }

    public enum SeatStatus {
        AVAILABLE, RESERVED, PAID
    }

    public enum ReservationStatus {
        COMPLETED, CANCELLED
    }

    public enum PaymentStatus {
        SUCCESS, FAILED
    }

    public enum PaymentMethod {
        CARD, BANK_TRANSFER
    }

    public enum TicketStatus {
        ISSUED, CANCELLED
    }
}
