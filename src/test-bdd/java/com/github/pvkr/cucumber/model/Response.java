package com.github.pvkr.cucumber.model;

import lombok.Data;

@Data
public class Response<T> {
    private int status;
    private T content;
}
