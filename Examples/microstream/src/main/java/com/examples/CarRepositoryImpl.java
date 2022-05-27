package com.examples;

import com.examples.shared.Car;
import one.microstream.storage.embedded.types.EmbeddedStorage;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class CarRepositoryImpl implements CarRepository
{
    private final List<Car> carList;
    private final EmbeddedStorageManager storage;

    public CarRepositoryImpl(@Value("${microstream.store.location}") final String location)
    {
        super();

        this.carList = new ArrayList<>();

        this.storage   = EmbeddedStorage.start(
                this.carList,
                Paths.get(location)
        );
    }

    @Override
    public void removeAll() {
        this.carList.clear();
        this.storeAll();
    }

    @Override
    public void storeAll()
    {
        this.storage.store(this.carList);
    }

    @Override
    public void add(final Car customer)
    {
        this.carList.add(customer);
        this.storeAll();
    }

    @Override
    public List<Car> findAll()
    {
        return this.carList;
    }
}