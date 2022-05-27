package com.examples.graphql;

import com.google.common.collect.ImmutableMap;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class GraphQLDataFetchers {

    private static List<Map<String, String>> accounts = Arrays.asList(
            ImmutableMap.of("id", "account-1",
                    "name", "James account",
                    "userId", "user-1"),
            ImmutableMap.of("id", "account-2",
                    "name", "Patrick's account",
                    "authorId", "user-2"),
            ImmutableMap.of("id", "account-3",
                    "name", "Mike's account",
                    "userId", "user-3")
    );

    private static List<Map<String, String>> users = Arrays.asList(
            ImmutableMap.of("id", "user-1",
                    "firstName", "James",
                    "lastName", "Gosling"),
            ImmutableMap.of("id", "user-2",
                    "firstName", "Patrick",
                    "lastName", "Naughton"),
            ImmutableMap.of("id", "user-3",
                    "firstName", "Mike",
                    "lastName", "Sheridan")
    );

    public DataFetcher getAccountByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            String accountId = dataFetchingEnvironment.getArgument("id");
            return accounts
                    .stream()
                    .filter(account -> account.get("id").equals(accountId))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher getUserDataFetcher() {
        return dataFetchingEnvironment -> {
            Map<String,String> book = dataFetchingEnvironment.getSource();
            String userId = book.get("userId");
            return users
                    .stream()
                    .filter(user -> user.get("id").equals(userId))
                    .findFirst()
                    .orElse(null);
        };
    }
}