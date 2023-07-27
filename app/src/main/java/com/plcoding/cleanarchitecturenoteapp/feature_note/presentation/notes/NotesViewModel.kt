package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.model.Note
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.use_case.NoteUseCasesWrapper
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.NoteOrder
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel@Inject constructor(
    private val noteUseCasesWrapper: NoteUseCasesWrapper
):ViewModel(){

    private val _state = mutableStateOf(NotesState())
    val state:State<NotesState> =_state

    private var recentlyDeletedNote: Note?=null

    private var getNotesJob:Job?=null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }
    fun onEvent(event: NotesEvent){

        when(event){
            is NotesEvent.Order ->{

                //? we are comparing ::class because we want to compare if class is same
                //? without ::class it will check if reference is same which will not happen
                //? because even if class is same it will return false cause it is checking
                //? reference like address/object of two classes which will be different

                //? we are not using ::class for orderType because they are OBJECTS not CLASSES

                //! NoteOrder represent order of Title ,Date,Color
                //! Order Type Represent ASCENDING OR DESCENDING ORDER

                if(state.value.noteOrder::class == event.notesOrder::class &&
                    state.value.noteOrder.orderType == event.notesOrder.orderType
                    ){

                    return
                    }
                
                getNotes(event.notesOrder)
            }

            is NotesEvent.DeleteNote ->{

                viewModelScope.launch {

                    noteUseCasesWrapper.deleteNoteUseCase(event.note)
                    recentlyDeletedNote =event.note

                }
            }

            is NotesEvent.RestoreNote ->{

                viewModelScope.launch {

                    noteUseCasesWrapper.addNoteUseCase(recentlyDeletedNote?: return@launch)
                    recentlyDeletedNote=null
                }
            }

            is NotesEvent.ToggleOrderSection ->{

                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }

    }

    private fun getNotes(notesOrder: NoteOrder) {

        getNotesJob?.cancel()

        getNotesJob=noteUseCasesWrapper.getNoteUseCase(notesOrder)
            .onEach { notes->

                _state.value =state.value.copy(
                    notes=notes,
                    noteOrder=notesOrder
                )
            }
            .launchIn(viewModelScope)
    }

}