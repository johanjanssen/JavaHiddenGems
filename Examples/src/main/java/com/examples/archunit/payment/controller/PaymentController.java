package com.examples.archunit.payment.controller;

import com.examples.archunit.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    private ReservationService reservationService;

    @Autowired
    public PaymentController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public void paymentFunction() {
        reservationService.reservationServiceFunction();
    }
}
