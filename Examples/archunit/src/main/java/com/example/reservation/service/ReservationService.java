package com.example.reservation.service;

import com.example.reservation.controller.ReservationController;
import org.springframework.beans.factory.annotation.Autowired;

public class ReservationService {
    private ReservationController reservationController;

    @Autowired
    public ReservationService(ReservationController reservationController) {
        this.reservationController = reservationController;
    }

    public void reservationServiceFunction() {
        System.out.println("ReservationServiceFunction");
        reservationController.reservationControllerFunction();
    }
}
