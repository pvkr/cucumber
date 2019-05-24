package com.github.pvkr.cucumber;

import com.github.pvkr.cucumber.controller.NoteController;
import com.github.pvkr.cucumber.model.Note;
import com.github.pvkr.cucumber.repository.NoteRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NoteControllerTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteController sut;

    @Test
    public void shouldGetAllNotes() {
        // GIVEN
        Note note1 = new Note();
        Note note2 = new Note();
        when(noteRepository.findAll()).thenReturn(Arrays.asList(note1, note2));
        // WHEN
        List<Note> allNotes = sut.getAllNotes();
        // THEN
        assertThat(allNotes, contains(note1, note2));
    }
}
