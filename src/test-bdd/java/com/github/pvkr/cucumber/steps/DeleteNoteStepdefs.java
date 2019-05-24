package com.github.pvkr.cucumber.steps;

import com.github.pvkr.cucumber.api.NoteClient;
import com.github.pvkr.cucumber.model.Response;
import com.github.pvkr.cucumber.state.CurrentNote;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.http.HttpStatus;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DeleteNoteStepdefs {

    private final NoteClient noteClient;
    private final CurrentNote currentNote;
    private Response<Void> response;

    public DeleteNoteStepdefs(NoteClient noteClient, CurrentNote currentNote) {
        this.noteClient = noteClient;
        this.currentNote = currentNote;
    }

    @When("^user deletes current note$")
    public void userDeletesCurrentNote() {
        response = noteClient.deleteNote(currentNote.getNote().getId());
    }

    @Then("^server should remove note$")
    public void serverShouldRemoveNote() {
        assertThat(response.getStatus(), is(HttpStatus.SC_OK));
    }
}
