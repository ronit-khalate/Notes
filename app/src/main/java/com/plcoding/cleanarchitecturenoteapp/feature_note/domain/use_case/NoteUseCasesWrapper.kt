package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case

data class NoteUseCasesWrapper(
    val getNotesUseCase:GetNotesUseCase,
    val deleteNoteUseCase: DeleteNodeUseCase,
    val addNoteUseCase: AddNoteUseCase,
    val getNoteUseCase: GetNoteUseCase
)