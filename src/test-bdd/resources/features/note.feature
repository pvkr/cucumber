Feature: Note management
  Users should be able to manage notes via requests to a web service

  @CleanCreatedNote
  Scenario: Create note
    When user posts note with title "hello" and content "Hello World"
    Then server should create note

  @CleanGivenNote
  Scenario: Get note
    Given current note with title "hello" and content "Hello World"
    When user gets current note
    Then server should return note

  @CleanGivenNote
  Scenario: Update note
    Given current note with title "hello" and content "Hello World"
    When user updates current note title to "test" and content to "Test message"
    Then server should update note

  @CleanGivenNote
  Scenario: Delete note
    Given current note with title "hello" and content "Hello World"
    When user deletes current note
    Then server should remove note

  @CleanGivenNote
  Scenario: Get all notes
    Given current note with title "hello" and content "Hello World"
    When users gets all notes
    Then server should receive current note
