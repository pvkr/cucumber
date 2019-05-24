package com.github.pvkr.cucumber.steps;

import com.github.pvkr.cucumber.api.NoteClient;
import com.github.pvkr.cucumber.model.Note;
import com.github.pvkr.cucumber.model.Response;
import com.github.pvkr.cucumber.state.CurrentNote;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import org.apache.http.HttpStatus;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class GivenStepdefs {

    private final NoteClient noteClient;
    private final CurrentNote currentNote;

    public GivenStepdefs(NoteClient noteClient, CurrentNote currentNote) {
        this.noteClient = noteClient;
        this.currentNote = currentNote;
    }

    @Given("^current note with title \"([^\"]*)\" and content \"([^\"]*)\"$")
    public void noteWithTitleAndContent(String title, String content) {
        Response<Note> response = noteClient.createNote(new Note(title, content));
        assertThat(response.getStatus(), is(HttpStatus.SC_OK));
        currentNote.setNote(response.getContent());
    }

    @After("@CleanGivenNote")
    public void cleanCurrentNote() {
        if (currentNote.getNote() != null) {
            noteClient.deleteNote(currentNote.getNote().getId());
        }
    }
}
