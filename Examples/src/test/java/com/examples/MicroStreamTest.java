package com.examples;

import com.examples.jobrunr.JobrunrStorageConfiguration;
import com.examples.microstream.CarRepository;
import com.examples.microstream.MicroStreamApplication;
import com.examples.shared.Car;
import com.examples.shared.CarPart;
import one.microstream.storage.embedded.types.EmbeddedStorage;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.inject.Inject;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {MicroStreamApplication.class, JobrunrStorageConfiguration.class})
public class MicroStreamTest {

    @Inject
    private CarRepository carRepository;

    @Test
    void testMicroStream() {
        final EmbeddedStorageManager storageManager = EmbeddedStorage.start();

        CarPart porscheCarPart1 = new CarPart("wheel", 23.23);
        Car porsche = new Car("Porsche", "911", List.of(porscheCarPart1));

        storageManager.setRoot(porsche);
        storageManager.storeRoot();
        final Object root = storageManager.root();

        String porscheExpected = "Car{brand='Porsche', model='911', carPartList=[CarPart{name='wheel', price=23.23}]}";
        assertTrue(root instanceof Car);
        if (root instanceof Car car) {
            assertEquals(porscheExpected, car.toString());
        }

        storageManager.shutdown();
    }

    @Test
    void testMicroStreamSpringBoot() {
        carRepository.removeAll(); // To facilitate multiple test runs
        CarPart porscheCarPart1 = new CarPart("wheel", 23.23);
        Car porsche = new Car("Porsche", "911", List.of(porscheCarPart1));

        CarPart lamborghiniCarPart1 = new CarPart("mirror", 232.25);
        Car lamborghini = new Car("Lamborghini", "Diablo", List.of(lamborghiniCarPart1));

        CarPart paganiCarPart = new CarPart("engine", 8342.28);
        Car pagani = new Car("Pagani", "Huayra", List.of(paganiCarPart));

        List<Car> retrievedCarList = carRepository.findAll();
        assertEquals(0, retrievedCarList.size());

        carRepository.add(porsche);
        carRepository.add(lamborghini);
        carRepository.add(pagani);

        //final Consumer<Car> logAll = c -> LOG.info(c.toString());

        retrievedCarList = carRepository.findAll();
        assertEquals(3, retrievedCarList.size());
        String lamborghiniExpected = "Car{brand='Lamborghini', model='Diablo', carPartList=[CarPart{name='mirror', price=232.25}]}";
        assertEquals(lamborghiniExpected, retrievedCarList.get(1).toString());

        carRepository.findAll().forEach(car -> car.setBrand(car.getBrand() + " rebranded"));
        carRepository.storeAll();

        retrievedCarList = carRepository.findAll();
        assertEquals(3, retrievedCarList.size());
        assertEquals("Porsche rebranded", retrievedCarList.get(0).getBrand());
        assertEquals("Lamborghini rebranded", retrievedCarList.get(1).getBrand());
        assertEquals("Pagani rebranded", retrievedCarList.get(2).getBrand());
    }
}
