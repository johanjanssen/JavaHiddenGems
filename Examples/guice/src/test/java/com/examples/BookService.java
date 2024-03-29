package com.examples;

import com.google.inject.Inject;

class BookService {
    @Inject
    private PrintService printService;

    public String handleBook() {
        return printService.enrichInfo("The Hobbit");
    }
}
