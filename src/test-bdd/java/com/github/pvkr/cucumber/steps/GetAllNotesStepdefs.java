package com.github.pvkr.cucumber.steps;

import com.github.pvkr.cucumber.api.NoteClient;
import com.github.pvkr.cucumber.model.Note;
import com.github.pvkr.cucumber.model.Response;
import com.github.pvkr.cucumber.state.CurrentNote;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.http.HttpStatus;

import java.util.List;

import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class GetAllNotesStepdefs {

    private final NoteClient noteClient;
    private final CurrentNote currentNote;
    private Response<List<Note>> response;

    public GetAllNotesStepdefs(NoteClient noteClient, CurrentNote currentNote) {
        this.noteClient = noteClient;
        this.currentNote = currentNote;
    }

    @When("^users gets all notes$")
    public void usersGetsAllNotes() {
        response = noteClient.getAll();
    }

    @Then("^server should receive (\\d+) notes$")
    public void serverShouldReceiveNotes(int noteCount) {
        assertThat(response.getStatus(), is(HttpStatus.SC_OK));
        assertThat(response.getContent(), hasSize(noteCount));
    }

    @Then("^server should receive current note$")
    public void serverShouldReceiveCurrentNote() {
        assertThat(response.getStatus(), is(HttpStatus.SC_OK));
        assertThat(response.getContent(), hasItem(
                both(hasProperty("id", equalTo(currentNote.getNote().getId())))
                        .and(hasProperty("title", equalTo(currentNote.getNote().getTitle())))
                        .and(hasProperty("content", equalTo(currentNote.getNote().getContent())))
        ));
    }
}
