package com.github.pvkr.cucumber.steps;

import com.github.pvkr.cucumber.api.NoteClient;
import com.github.pvkr.cucumber.model.Note;
import com.github.pvkr.cucumber.model.Response;
import com.github.pvkr.cucumber.state.CurrentNote;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.http.HttpStatus;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class UpdateNoteStepdefs {

    private final NoteClient noteClient;
    private final CurrentNote currentNote;
    private Response<Note> response;
    private String title;
    private String content;

    public UpdateNoteStepdefs(NoteClient noteClient, CurrentNote currentNote) {
        this.noteClient = noteClient;
        this.currentNote = currentNote;
    }

    @When("^user updates current note title to \"([^\"]*)\" and content to \"([^\"]*)\"$")
    public void userUpdatesCurrentNoteTitleAndContent(String title, String content) {
        this.title = title;
        this.content = content;
        response = noteClient.updateNote(currentNote.getNote().getId(), title, content);
    }

    @Then("^server should update note$")
    public void serverShouldUpdateNote() {
        assertThat(response.getStatus(), is(HttpStatus.SC_OK));
        assertThat(response.getContent().getId(), is(currentNote.getNote().getId()));
        assertThat(response.getContent().getTitle(), is(title));
        assertThat(response.getContent().getContent(), is(content));
        assertThat(response.getContent().getCreatedAt(), is(currentNote.getNote().getCreatedAt()));
        assertThat(response.getContent().getUpdatedAt(), not(is(currentNote.getNote().getUpdatedAt())));
    }
}
