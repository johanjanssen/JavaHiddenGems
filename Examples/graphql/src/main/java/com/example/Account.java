package com.example;

import java.util.Arrays;
import java.util.List;

public record Account (String id, int number, String userId){
    private static List<Account> accounts = Arrays.asList(
            new Account("account-1", 42, "user-1"),
            new Account("account-2", 21, "user-2"),
            new Account("account-3", 84, "user-3")
    );

    public static Account getById(String id) {
        return accounts.stream()
                .filter(account -> account.id().equals(id))
                .findFirst()
                .orElse(null);
    }
}
