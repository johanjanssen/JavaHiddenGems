package com.example;

import java.util.List;

public class Car {
    private String brand;
    private String model;
    private List<CarPart> carPartList;

    public Car() {
    }

    public Car(String brand, String model, List<CarPart> carPartList) {
        this.brand = brand;
        this.model = model;
        this.carPartList = carPartList;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<CarPart> getCarPartList() {
        return carPartList;
    }

    public void setCarPartList(List<CarPart> carPartList) {
        this.carPartList = carPartList;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", carPartList=" + carPartList +
                '}';
    }
}
