package com.examples;

import com.examples.graphql.GraphQLApplication;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = GraphQLApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GraphQLExampleTest {

    @LocalServerPort
    private int port;

    @Test
    void testGraphQL() {
        GraphQLQuery query = new GraphQLQuery();
        String queryFirstAccount = """
                query {
                    accountById(id: "account-1") {
                        id
                        name
                    }
                }
                """;
        query.setQuery(queryFirstAccount);

        RestAssured.baseURI = "http://localhost:" + port;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.contentType(ContentType.JSON);
        httpRequest.body(query);
        Response response = httpRequest.post("/graphql");

        String expected = """
                {"data":{"accountById":{"id":"account-1","name":"James account"}}}""";
        assertEquals(expected, response.getBody().asString());

        String queryFirstAccountWithUser = """
                query {
                    accountById(id: "account-1") {
                        id
                        name
                        user {
                            firstName
                            lastName
                        }
                    }
                }
                """;

        query.setQuery(queryFirstAccountWithUser);
        httpRequest.body(query);
        response = httpRequest.post("/graphql");

        expected = """
                {"data":{"accountById":{"id":"account-1","name":"James account","user":{"firstName":"James","lastName":"Gosling"}}}}""";
        assertEquals(expected, response.getBody().asString());
    }

    public class GraphQLQuery {

        private String query;
        private Object variables;


        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        public Object getVariables() {
            return variables;
        }

        public void setVariables(Object variables) {
            this.variables = variables;
        }
    }
}