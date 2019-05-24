package com.github.pvkr.cucumber.api.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.github.pvkr.cucumber.api.NoteClient;
import com.github.pvkr.cucumber.model.Note;
import com.github.pvkr.cucumber.model.Response;
import lombok.SneakyThrows;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.util.List;

public class NoteClientImpl implements NoteClient {

    private String baseUrl;
    private ObjectMapper objectMapper;

    public NoteClientImpl() {
        this.objectMapper = new ObjectMapper();
        baseUrl = System.getProperty("note.url");
    }

    @Override
    @SneakyThrows
    public Response<Note> createNote(Note note) {
        HttpResponse response = Request.Post(baseUrl + "/notes")
                .bodyString(toJson(note), ContentType.APPLICATION_JSON)
                .execute().returnResponse();

        return of(response, Note.class);
    }

    @Override
    @SneakyThrows
    public Response<Note> getNoteById(Long noteId) {
        HttpResponse response = Request.Get(baseUrl + "/notes/" + noteId)
                .execute().returnResponse();

        return of(response, Note.class);
    }

    @Override
    @SneakyThrows
    public Response<Note> updateNote(Long noteId, String title, String content) {
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        HttpResponse response = Request.Put(baseUrl + "/notes/" + noteId)
                .bodyString(toJson(note), ContentType.APPLICATION_JSON)
                .execute().returnResponse();

        return of(response, Note.class);
    }

    @Override
    @SneakyThrows
    public Response<Void> deleteNote(Long noteId) {
        HttpResponse response = Request.Delete(baseUrl + "/notes/" + noteId)
                .execute().returnResponse();

        return of(response);
    }

    @Override
    @SneakyThrows
    public Response<List<Note>> getAll() {
        HttpResponse response = Request.Get(baseUrl + "/notes")
                .execute().returnResponse();
        return listOf(response, Note.class);
    }

    @Override
    @SneakyThrows
    public Response<Void> deleteAll() {
        return of(Request.Delete(baseUrl + "/notes").execute().returnResponse());
    }

    private String toJson(Note note) throws JsonProcessingException {
        return objectMapper.writer().writeValueAsString(note);
    }

    private <T> Response<List<T>> listOf(HttpResponse httpResponse, Class<T> clazz) throws IOException {
        CollectionType clazzListType = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
        Response<List<T>> response = new Response<>();
        response.setStatus(httpResponse.getStatusLine().getStatusCode());
        response.setContent(objectMapper.readerFor(clazzListType).readValue(httpResponse.getEntity().getContent()));
        return response;
    }

    private <T> Response<T> of(HttpResponse httpResponse, Class<T> clazz) throws IOException {
        Response<T> response = new Response<>();
        response.setStatus(httpResponse.getStatusLine().getStatusCode());
        response.setContent(objectMapper.readerFor(clazz).readValue(httpResponse.getEntity().getContent()));
        return response;
    }

    private Response<Void> of(HttpResponse httpResponse) {
        Response<Void> response = new Response<>();
        response.setStatus(httpResponse.getStatusLine().getStatusCode());
        return response;
    }
}
