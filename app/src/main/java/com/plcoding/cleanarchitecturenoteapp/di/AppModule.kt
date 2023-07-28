package com.plcoding.cleanarchitecturenoteapp.di

import android.app.Application
import androidx.room.Room
import com.plcoding.cleanarchitecturenoteapp.feature_note.data.data_source.NoteDatabase
import com.plcoding.cleanarchitecturenoteapp.feature_note.data.repository.NoteRepositoryImpl
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.repository.NoteRepository
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case.AddNoteUseCase
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case.DeleteNodeUseCase
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case.GetNoteUseCase
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case.GetNotesUseCase
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case.NoteUseCasesWrapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //! provides instance of room database
    @Provides
    @Singleton
    fun provideNoteDatabase(app:Application):NoteDatabase{

        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME

        ).build()

    }


    //! provides instance of NoteRepository
    @Provides
    @Singleton
    fun provideNoteRepository(db:NoteDatabase):NoteRepository{

        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCasesWrapper(noteRepository: NoteRepository):NoteUseCasesWrapper{

        return NoteUseCasesWrapper(

            getNotesUseCase = GetNotesUseCase(noteRepository),
            deleteNoteUseCase = DeleteNodeUseCase(noteRepository),
            addNoteUseCase = AddNoteUseCase(noteRepository),
            getNoteUseCase = GetNoteUseCase(noteRepository)
            )

    }
}