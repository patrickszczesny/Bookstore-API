package com.example.BookStore.repository;

import com.example.BookStore.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class BookRepositoryImpl implements BookRepositoryCustom {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public List<Book> searchSpringBooks(int limit) {
        Query query = new Query();
        query.limit(limit);
        query.addCriteria(Criteria.where("title").regex("Spring"));
        return mongoOperations.find(query, Book.class);
    }
}
