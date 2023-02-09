package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.graphql.test.tester.GraphQlTester;

@GraphQlTest(AccountController.class)
public class AccountControllerTests {

    @Autowired
    private GraphQlTester graphQlTester;

    @Test
    void shouldGetFirstAccount() {
        this.graphQlTester
                .documentName("account")
                .variable("id", "account-1")
                .execute()
                .path("accountById")
                .matchesJson("""
                    {
                        "id": "account-1",
                        "number": 42
                    }
                """);
    }
    @Test
    void shouldGetFirstAccountWithUser() {
        this.graphQlTester
				.documentName("accountWithUser")
				.variable("id", "account-1")
                .execute()
                .path("accountById")
                .matchesJson("""
                    {
                        "id": "account-1",
                        "number": 42,
                        "user": {
                          "firstName": "James",
                          "lastName": "Gosling"
                        }
                    }
                """);
    }
}
