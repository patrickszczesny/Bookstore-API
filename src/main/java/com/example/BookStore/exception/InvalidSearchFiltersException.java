package com.example.BookStore.exception;

public class InvalidSearchFiltersException extends RuntimeException {
    public InvalidSearchFiltersException() {
        super("Invalid search filters. Choose title OR author.");
    }
}
