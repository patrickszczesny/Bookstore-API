package com.example.BookStore.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IcndbResponseDto {
    private final IcndbValueDto value;

    @JsonCreator
    public IcndbResponseDto(@JsonProperty("value") IcndbValueDto value) {
        this.value = value;
    }

    public IcndbValueDto getValue() {
        return value;
    }
}
