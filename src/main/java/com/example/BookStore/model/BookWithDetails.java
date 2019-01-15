package com.example.BookStore.model;

public class BookWithDetails {
    private final String title;
    private final String author;
    private final String description;

    public BookWithDetails(String title, String author, String description) {
        this.title = title;
        this.author = author;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }
}
