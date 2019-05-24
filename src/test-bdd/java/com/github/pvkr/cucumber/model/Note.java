package com.github.pvkr.cucumber.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Note {

    public Note() {
    }

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }

    private Long id;
    private String title;
    private String content;
    private String createdAt;
    private String updatedAt;
}
