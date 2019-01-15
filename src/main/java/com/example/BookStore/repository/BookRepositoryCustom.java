package com.example.BookStore.repository;

import com.example.BookStore.model.Book;

import java.util.List;

public interface BookRepositoryCustom {
    List<Book> searchSpringBooks(int limit);
}
