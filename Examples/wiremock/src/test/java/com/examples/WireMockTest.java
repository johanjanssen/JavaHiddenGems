package com.examples;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@com.github.tomakehurst.wiremock.junit5.WireMockTest
public class WireMockTest {


    @Test
    void testWireMock(WireMockRuntimeInfo wmRuntimeInfo) throws URISyntaxException, IOException, InterruptedException {
        Gson gson = new Gson();
        Car car = new Car("Fiat", "Punto");

        stubFor(get("/car").willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(gson.toJson(car))));

        final HttpClient client = HttpClient.newBuilder().build();

        final HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:" + wmRuntimeInfo.getHttpPort() + "/car"))
                .header("Content-Type", "text/xml")
                .GET().build();

        final HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        String expectedFiat = """
                {"brand":"Fiat","model":"Punto"}""";
        Assertions.assertEquals(expectedFiat, response.body().toString());
    }
}
