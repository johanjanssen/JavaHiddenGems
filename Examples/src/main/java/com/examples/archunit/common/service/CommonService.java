package com.examples.archunit.common.service;

import com.examples.archunit.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;

public class CommonService {
    private ReservationService reservationService;

    @Autowired
    public CommonService(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    public void commonFunction() {
        reservationService.reservationServiceFunction();
    }
}
