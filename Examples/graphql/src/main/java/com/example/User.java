package com.example;

import java.util.Arrays;
import java.util.List;

public record User(String id, String firstName, String lastName){

    private static List<User> users = Arrays.asList(
            new User("user-1", "James", "Gosling"),
            new User("user-2", "Patrick", "Naughton"),
            new User("user-3", "Mike", "Sheridan")
    );

    public static User getById(String id) {
        return users.stream()
                .filter(user -> user.id().equals(id))
                .findFirst()
                .orElse(null);
    }
}
