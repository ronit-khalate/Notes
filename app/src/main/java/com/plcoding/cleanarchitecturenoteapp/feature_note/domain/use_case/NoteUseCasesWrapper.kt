package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case

data class NoteUseCasesWrapper(
    val getNoteUseCase:GetNotesUseCase,
    val deleteNoteUseCase: DeleteNodeUseCase
)