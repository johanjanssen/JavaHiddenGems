package com.examples.autoservice;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AutoServiceTest {

    @Test
    void testAutoService() {
        ServiceLoader<CarService> carServiceServiceLoader = ServiceLoader.load(CarService.class);
        assertEquals(2, carServiceServiceLoader.stream().count());

        List<CarService> carServiceList = new ArrayList<>();
        carServiceServiceLoader.stream().forEach(c -> carServiceList.add(c.get()));

        for (CarService carService: carServiceList) {
            if (carService instanceof PaganiCarService) {
                assertEquals("Pagani", carService.retrieveBrand());
            } else if (carService instanceof PorscheCarService) {
                assertEquals("Porsche", carService.retrieveBrand());
            }
        }
    }
}
