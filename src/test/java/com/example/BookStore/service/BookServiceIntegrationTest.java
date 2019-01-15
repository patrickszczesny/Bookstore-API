package com.example.BookStore.service;

import com.example.BookStore.model.Book;
import com.example.BookStore.model.BookListing;
import com.example.BookStore.repository.BookRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceIntegrationTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @Test
    public void getListing() throws Exception {
        // given
        bookRepository.deleteAll();
        bookRepository.save(new Book("1", "title 1", "autor 1"));
        bookRepository.save(new Book("2", "title 2", "autor 2"));
        bookRepository.save(new Book("3", "title 3", "autor 1"));

        // when
        BookListing bookListing = bookService.getListing();

        // then
        Assertions.assertThat(bookListing.getNumberOfBooks()).isEqualTo(3);
        Assertions.assertThat(bookListing.getBooks()).isEqualTo(
                Lists.newArrayList(
                        new Book("1", "title 1", "autor 1"),
                        new Book("2", "title 2", "autor 2"),
                        new Book("3", "title 3", "autor 1")
                )
        );
    }

}