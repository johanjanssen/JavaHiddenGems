package com.example;

import com.example.graphql.model.client.AccountByIdGraphQLQuery;
import com.example.graphql.model.client.AccountByIdProjectionRoot;
import com.example.graphql.model.types.Account;
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.graphql.test.tester.GraphQlTester;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@GraphQlTest(AccountController.class)
public class AccountControllerTests {

    @Autowired
    private GraphQlTester graphQlTester;

    @Test
    void shouldGetFirstAccount() {
        // arrange
        GraphQLQueryRequest graphQLQueryRequest = new GraphQLQueryRequest(
                AccountByIdGraphQLQuery.newRequest().id("account-1").build()
                , new AccountByIdProjectionRoot().id().number() );
        // act
        Account accountById = this.graphQlTester
                .document(graphQLQueryRequest.serialize())
                .execute().path("accountById").entity(Account.class).get();
        // assert
        assertEquals("account-1", accountById.getId());
        assertEquals(42, accountById.getNumber());
        assertNull(accountById.getUser());
    }
    @Test
    void shouldGetFirstAccountWithUser() {
        // arrange
        GraphQLQueryRequest graphQLQueryRequest = new GraphQLQueryRequest(
                AccountByIdGraphQLQuery.newRequest().id("account-1").build()
                , new AccountByIdProjectionRoot().id().number().user().firstName().lastName() );
        // act
        Account accountById = this.graphQlTester
                .document(graphQLQueryRequest.serialize())
                .execute().path("accountById").entity(Account.class).get();
        // assert
        assertEquals("account-1", accountById.getId());
        assertEquals(42, accountById.getNumber());
        assertEquals("James", accountById.getUser().getFirstName());
        assertEquals("Gosling", accountById.getUser().getLastName());
    }
}
