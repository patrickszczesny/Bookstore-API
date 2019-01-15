package com.example.BookStore.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IcndbValueDto {
    private final String joke;

    @JsonCreator
    public IcndbValueDto(@JsonProperty("joke") String joke) {
        this.joke = joke;
    }

    public String getJoke() {
        return joke;
    }
}
