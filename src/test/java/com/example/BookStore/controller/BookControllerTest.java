package com.example.BookStore.controller;

import com.example.BookStore.model.Book;
import com.example.BookStore.repository.BookRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Before
    public void setUp() {
        bookRepository.deleteAll();
    }

    @After
    public void tearDown() {
        bookRepository.deleteAll();
    }

    @Test
    public void shouldReturnExistingBook() throws Exception {
        // given
        Book first = bookRepository.save(new Book("title 1", "autor 1"));
        Book second = bookRepository.save(new Book("title 2", "autor 2"));

        // when
        mockMvc.perform(get("/books/" + second.getId()).accept(MediaType.APPLICATION_JSON_UTF8))
                // then
        .andExpect(status().isOk())
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(content().json(asJsonString(new Book(second.getId(), "title 2", "autor 2"))));
    }

    @Test
    public void shouldReturn404Error() throws Exception {
        // when
        mockMvc.perform(get("/books/not-existing").accept(MediaType.APPLICATION_JSON_UTF8))
                // then
                .andExpect(status().isNotFound());
    }

    private String asJsonString(Book book) {
        try {
            return new ObjectMapper().writeValueAsString(book);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }

}