package com.examples;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@com.github.tomakehurst.wiremock.junit5.WireMockTest
public class WireMockTest {

    @Test
    void testWireMock(WireMockRuntimeInfo wmRuntimeInfo) {
        Gson gson = new Gson();
        Car car = new Car("Fiat", "Punto");

        stubFor(get("/car").willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(gson.toJson(car))));

        RestTemplate restTemplate = new RestTemplate();
        String carResourceURL
                = "http://localhost:" + wmRuntimeInfo.getHttpPort() + "/car";
        ResponseEntity<Car> response
                = restTemplate.getForEntity(carResourceURL, Car.class);
        String expectedFiat = "Car{brand='Fiat', model='Punto'}";
        Assertions.assertEquals(expectedFiat, response.getBody().toString());
    }
}
