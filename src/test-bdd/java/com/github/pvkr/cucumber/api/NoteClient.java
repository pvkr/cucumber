package com.github.pvkr.cucumber.api;

import com.github.pvkr.cucumber.model.Note;
import com.github.pvkr.cucumber.model.Response;

import java.util.List;

public interface NoteClient {

    Response<Note> createNote(Note note);

    Response<Note> getNoteById(Long noteId);

    Response<Note> updateNote(Long noteId, String title, String content);

    Response<Void> deleteNote(Long noteId);

    Response<List<Note>> getAll();

    Response<Void> deleteAll();
}
