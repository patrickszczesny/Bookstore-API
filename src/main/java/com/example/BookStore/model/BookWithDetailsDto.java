package com.example.BookStore.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookWithDetailsDto {
    private final String title;
    private final String author;
    private final String description;

    @JsonCreator
    public BookWithDetailsDto(
            @JsonProperty("title") String title,
            @JsonProperty("author") String author,
            @JsonProperty("description") String description) {
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

    public static BookWithDetailsDto fromDomain(BookWithDetails bookWithDetails) {
        return new BookWithDetailsDto(
                bookWithDetails.getTitle(),
                bookWithDetails.getAuthor(),
                bookWithDetails.getDescription());
    }
}
