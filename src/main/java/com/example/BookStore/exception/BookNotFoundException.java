package com.example.BookStore.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String bookId) {
        super(String.format("Book with id %s not found", bookId));
    }
}
