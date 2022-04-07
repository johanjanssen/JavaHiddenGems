package com.examples;

import com.examples.shared.Car;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@com.github.tomakehurst.wiremock.junit5.WireMockTest
public class WireMockTest {

    @Test
    void testWireMock(WireMockRuntimeInfo wmRuntimeInfo) {
        Gson gson = new Gson();
        Car car = new Car("Fiat", "Punto", null);

        stubFor(get("/car").willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(gson.toJson(car))));

        RestTemplate restTemplate = new RestTemplate();
        String carResourceURL
                = "http://localhost:" + wmRuntimeInfo.getHttpPort() + "/car";
        ResponseEntity<Car> response
                = restTemplate.getForEntity(carResourceURL, Car.class);
        String expectedFiat = "Car{brand='Fiat', model='Punto', carPartList=null}";
        assertEquals(expectedFiat, response.getBody().toString());
    }
}
