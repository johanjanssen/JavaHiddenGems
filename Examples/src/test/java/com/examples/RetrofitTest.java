package com.examples;

import com.examples.shared.Car;
import com.examples.shared.CarApplication;
import com.examples.shared.CarPart;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.*;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes= CarApplication.class)
public class RetrofitTest {
    @LocalServerPort
    private int port;

    public interface CarService {
        @GET("car")
        Call<List<Car>> listCars();

        @POST("car")
        Call<Car> createCar(@Body Car car);
    }

    @Test
    void testRetrofit() throws IOException, InterruptedException {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:" + port + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CarService carService = retrofit.create(CarService.class);
        Call<List<Car>> callCarCollection = carService.listCars();
        Response<List<Car>> carCollection = callCarCollection.execute();
        List<Car> carList = carCollection.body();

        String paganiExpected = "Car{brand='Pagani', model='Huayra', carPartList=[CarPart{name='engine', price=8342.28}]}";
        assertEquals(1, carList.size());
        assertEquals(paganiExpected, carList.get(0).toString());

        // asynchronous
        CarPart porscheCarPart1 = new CarPart("wheel", 23.23);
        Car porsche = new Car("Porsche", "911", List.of(porscheCarPart1));
        Call<Car> create = carService.createCar(porsche);
        create.enqueue(new Callback<Car>() {
            @Override
            public void onResponse(Call<Car> call, Response<Car> response) {
                if(response.isSuccessful()) {
                    System.out.println("Saved car");
                }
            }

            @Override
            public void onFailure(Call<Car> call, Throwable throwable) {
                System.out.println("Saving car failed");
                System.out.println(throwable);
            }
        });

        Thread.sleep(100);

        // synchronous
        CarPart lamborghiniCarPart1 = new CarPart("mirror", 232.25);
        Car lamborghini = new Car("Lamborghini", "Diablo", List.of(lamborghiniCarPart1));
        Call<Car> createLamborghini = carService.createCar(lamborghini);
        createLamborghini.execute();

        callCarCollection = carService.listCars();
        carCollection = callCarCollection.execute();
        carList = carCollection.body();

        assertEquals(3, carList.size());
        assertEquals(paganiExpected, carList.get(0).toString());

        String porscheExpected = "Car{brand='Porsche', model='911', carPartList=[CarPart{name='wheel', price=23.23}]}";
        String lamborginiExpected = "Car{brand='Lamborghini', model='Diablo', carPartList=[CarPart{name='mirror', price=232.25}]}";
        assertEquals(porscheExpected, carList.get(1).toString());
        assertEquals(lamborginiExpected, carList.get(2).toString());
    }
}
