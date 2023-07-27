package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes

import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note

sealed class NotesEvent{

    data class Order(val notesState: NotesState):NotesEvent()
    data class DeleteNote(val note: Note):NotesEvent()
    object RestoreNote:NotesEvent()
    object ToggleOrderSection:NotesEvent()

}
