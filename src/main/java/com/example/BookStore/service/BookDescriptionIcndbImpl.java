package com.example.BookStore.service;

import com.example.BookStore.model.IcndbResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

//@Service
public class BookDescriptionIcndbImpl implements BookDescriptionClient {
    private final RestOperations restTemplate;
    private final String address;

    @Autowired
    public BookDescriptionIcndbImpl(
            RestOperations restTemplate,
            @Value("${icndb.url}") String address) {
        this.restTemplate = restTemplate;
        this.address = address;
    }
    @Override
    public String getDescription(String bookId) {
        return restTemplate.getForObject(address + bookId, IcndbResponseDto.class).getValue().getJoke();
    }
}
