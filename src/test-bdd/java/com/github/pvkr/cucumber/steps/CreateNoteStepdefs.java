package com.github.pvkr.cucumber.steps;

import com.github.pvkr.cucumber.api.NoteClient;
import com.github.pvkr.cucumber.model.Note;
import com.github.pvkr.cucumber.model.Response;
import cucumber.api.java.After;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.http.HttpStatus;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class CreateNoteStepdefs {

    private final NoteClient noteClient;
    private Response<Note> response;
    private String title;
    private String content;

    public CreateNoteStepdefs(NoteClient noteClient) {
        this.noteClient = noteClient;
    }

    @When("^user posts note with title \"([^\"]*)\" and content \"([^\"]*)\"$")
    public void userPostNoteWithTitleAndContent(String title, String content) {
        this.title = title;
        this.content = content;
        response = noteClient.createNote(new Note(title, content));
    }

    @Then("^server should create note$")
    public void serverShouldCreateNewNote() {
        assertThat(response.getStatus(), is(HttpStatus.SC_OK));
        assertThat(response.getContent().getId(), notNullValue());
        assertThat(response.getContent().getTitle(), is(title));
        assertThat(response.getContent().getContent(), is(content));
        assertThat(response.getContent().getCreatedAt(), notNullValue());
        assertThat(response.getContent().getUpdatedAt(), notNullValue());
    }

    @After("@CleanCreatedNote")
    public void cleanCreatedNote() {
        if (response.getContent() != null) {
            noteClient.deleteNote(response.getContent().getId());
        }
    }
}
