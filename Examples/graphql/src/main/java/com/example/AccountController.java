package com.example;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class AccountController {
    @QueryMapping
    public Account accountById(@Argument String id) {
        return Account.getById(id);
    }

    @SchemaMapping
    public User user(Account account) {
        return User.getById(account.userId());
    }
}