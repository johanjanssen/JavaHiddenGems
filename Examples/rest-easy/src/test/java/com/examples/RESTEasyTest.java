package com.examples;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import org.jboss.resteasy.plugins.providers.jackson.ResteasyJackson2Provider;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CarApplication.class)
@DirtiesContext
public class RESTEasyTest {
    @LocalServerPort
    private int port;

    @Test
    void testRESTEasy() throws InterruptedException {
        final String path = "http://localhost:" + port + "/car";

        final Client client = ClientBuilder.newBuilder().register(ResteasyJackson2Provider.class).build();
        WebTarget target = client.target(UriBuilder.fromPath(path));
        Response response = target.request().accept(MediaType.APPLICATION_JSON).get();
        List<Car> carList = response.readEntity(new GenericType<List<Car>>() {});

        String paganiExpected = "Car{brand='Pagani', model='Huayra', carPartList=[CarPart{name='engine', price=8342.28}]}";
        assertEquals(1, carList.size());
        assertEquals(paganiExpected, carList.get(0).toString());

        // asynchronous
        CarPart porscheCarPart1 = new CarPart("wheel", 23.23);
        Car porsche = new Car("Porsche", "911", List.of(porscheCarPart1));

        Future<Response> storedPorscheReponse = target.request().async()
                .post(Entity.entity(porsche, "application/json"));
        //assertEquals(200, storedPorscheReponse.getStatus());

        Thread.sleep(200);

        // synchronous
        CarPart lamborghiniCarPart1 = new CarPart("mirror", 232.25);
        Car lamborghini = new Car("Lamborghini", "Diablo", List.of(lamborghiniCarPart1));

        Response storedLamborghiniReponse = target.request()
                .post(Entity.entity(lamborghini, "application/json"));
        assertEquals(200, response.getStatus());

        response = target.request().accept(MediaType.APPLICATION_JSON).get();
        carList = response.readEntity(new GenericType<List<Car>>() {});

        String porscheExpected = "Car{brand='Porsche', model='911', carPartList=[CarPart{name='wheel', price=23.23}]}";
        String lamborghiniExpected = "Car{brand='Lamborghini', model='Diablo', carPartList=[CarPart{name='mirror', price=232.25}]}";
        assertEquals(3, carList.size());
        assertEquals(paganiExpected, carList.get(0).toString());
        assertEquals(porscheExpected, carList.get(1).toString());
        assertEquals(lamborghiniExpected, carList.get(2).toString());

        response.close();
    }
}
