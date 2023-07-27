package com.plcoding.cleanarchitecturenoteapp.feature_note.data.repository

import android.provider.ContactsContract
import com.plcoding.cleanarchitecturenoteapp.feature_note.data.data_source.NoteDao
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val dao:NoteDao
):NoteRepository{
    override fun getNotes(): Flow<List<Note>> {

        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun deleteNote(note: Note) {

        return dao.delete(note)
    }

    override suspend fun insertNote(note: Note) {
        return dao.insertNote(note)
    }

}