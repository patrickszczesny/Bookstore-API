package com.example.BookStore.repository;

import com.example.BookStore.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom {
    Book save(Book book);
    List<Book> findAll();
    List<Book> findByAuthorStartsWith(String author);
    List<Book> findByTitleStartsWith(String title);
    void deleteAll();
    void delete(Book book);
    Optional<Book> findById(String id);

    @Query("{title: ?0}")
    Optional<Book> findUsingMyQuery(String title);
}
