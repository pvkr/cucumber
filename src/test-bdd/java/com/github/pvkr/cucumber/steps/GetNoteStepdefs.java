package com.github.pvkr.cucumber.steps;

import com.github.pvkr.cucumber.api.NoteClient;
import com.github.pvkr.cucumber.model.Note;
import com.github.pvkr.cucumber.model.Response;
import com.github.pvkr.cucumber.state.CurrentNote;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.http.HttpStatus;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class GetNoteStepdefs {

    private final NoteClient noteClient;
    private final CurrentNote currentNote;
    private Response<Note> response;

    public GetNoteStepdefs(NoteClient noteClient, CurrentNote currentNote) {
        this.noteClient = noteClient;
        this.currentNote = currentNote;
    }

    @When("^user gets current note$")
    public void userGetsCurrentNote() {
        response = noteClient.getNoteById(currentNote.getNote().getId());
    }

    @Then("^server should return note$")
    public void serverShouldReturnNote() {
        assertThat(response.getStatus(), is(HttpStatus.SC_OK));
        assertThat(response.getContent().getId(), is(currentNote.getNote().getId()));
        assertThat(response.getContent().getTitle(), is(currentNote.getNote().getTitle()));
        assertThat(response.getContent().getContent(), is(currentNote.getNote().getContent()));
        assertThat(response.getContent().getCreatedAt(), is(currentNote.getNote().getCreatedAt()));
        assertThat(response.getContent().getUpdatedAt(), is(currentNote.getNote().getUpdatedAt()));
    }
}
