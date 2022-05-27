package com.examples;

import com.examples.shared.Car;
import com.examples.shared.CarApplication;
import com.examples.shared.CarPart;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DirtiesContext
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CarApplication.class)
public class RESTassuredTest {
    @LocalServerPort
    private int port;

    @Test
    void testRESTassured() {
        List<Car> carList =
                given()
                    .baseUri("http://localhost:" + port + "/")
                .when()
                    .get("/car")
                .then()
                    .extract()
                    .body()
                    .jsonPath().getList(".", Car.class);

        String paganiExpected = "Car{brand='Pagani', model='Huayra', carPartList=[CarPart{name='engine', price=8342.28}]}";

        assertEquals(1, carList.size());
        assertEquals(paganiExpected, carList.get(0).toString());

        CarPart porscheCarPart1 = new CarPart("wheel", 23.23);
        Car porsche = new Car("Porsche", "911", List.of(porscheCarPart1));

        ValidatableResponse validatableResponse =
                given()
                        .baseUri("http://localhost:" + port + "/")
                        .contentType(ContentType.JSON)
                        .body(porsche)
                .when()
                        .post("/car")
                .then()
                        .statusCode(HttpStatus.SC_OK);
        assertEquals(200, validatableResponse.extract().statusCode());


        List<Car> newCarList =
                given()
                        .baseUri("http://localhost:" + port + "/")
                .when()
                        .get("/car")
                .then()
                        .extract()
                        .body()
                        .jsonPath().getList(".", Car.class);
        System.out.println(newCarList.get(0).toString());
        assertEquals(2, newCarList.size());
        assertEquals(paganiExpected, newCarList.get(0).toString());

        String porscheExpected = "Car{brand='Porsche', model='911', carPartList=[CarPart{name='wheel', price=23.23}]}";
        assertEquals(porscheExpected, newCarList.get(1).toString());

    }
}
