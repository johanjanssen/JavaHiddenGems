package com.examples.mapstruct;

public class Book {
    private String title;
    private String bookValue;

    public Book() {
    }

    public Book(String title, String bookValue) {
        this.title = title;
        this.bookValue = bookValue;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBookValue() {
        return bookValue;
    }

    public void setBookValue(String bookValue) {
        this.bookValue = bookValue;
    }
}