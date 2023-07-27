package com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case

data class NoteUseCasesWrapper(
    val getNotes:GetNotesUseCase,
    val deleteNodeUseCase: DeleteNodeUseCase
)