package com.examples.microstream;

import com.examples.shared.Car;

import java.util.List;

public interface CarRepository
{
    void add(Car car);

    List<Car> findAll();

    void removeAll();

    void storeAll();
}