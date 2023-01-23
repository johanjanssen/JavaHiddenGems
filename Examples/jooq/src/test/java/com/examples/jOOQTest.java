package com.examples;

import com.examples.jooq.generated.tables.records.CarRecord;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.examples.jooq.generated.tables.Car.CAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

// Also see src/main/resources/schema.sql
@SpringBootTest(classes = {jOOQApplication.class})
public class jOOQTest {
    @Autowired
    private DSLContext dslContext;

    @Test
    void testjOOQ() {
        dslContext.delete(CAR).execute();

        CarRecord porsche = new CarRecord(1, "Porsche", "911");
        saveCar(porsche);

        CarRecord lamborghini = new CarRecord(2, "Lamborghini", "Diablo");
        saveCar(lamborghini);

        Result<CarRecord> cars = dslContext.selectFrom(CAR).fetch();
        assertEquals(2, cars.size());
        assertEquals("Porsche", cars.get(0).getBrand());
        assertEquals("Lamborghini", cars.get(1).getBrand());
    }

    public void saveCar(CarRecord carRecord) {
        dslContext.insertInto(CAR)
                .set(CAR.ID, carRecord.getId())
                .set(CAR.BRAND, carRecord.getBrand())
                .set(CAR.MODEL, carRecord.getModel()).execute();
    }
}
