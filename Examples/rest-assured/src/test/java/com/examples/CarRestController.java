package com.examples;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CarRestController {
    List<Car> carList = new ArrayList<>();

    public CarRestController() {
        CarPart huayraCarPart = new CarPart("engine", 8342.28);
        Car huayra = new Car("Pagani", "Huayra", List.of(huayraCarPart));
        carList.add(huayra);
    }

    @GetMapping("/car")
    public List<Car> retrieveCar() {
        return carList;
    }

    @PostMapping(path = "/car")
    public Car addCar(@RequestBody Car car) {
        carList.add(car);
        return car;

    }
}