package com.example.registroocupaciones.presentation.ocupacion.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registroocupaciones.domain.ocupaciones.model.Ocupacion
import com.example.registroocupaciones.domain.ocupaciones.usecase.DeleteOcupacionUseCase
import com.example.registroocupaciones.domain.ocupaciones.usecase.ObserveOcupacionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListOcupacionViewModel @Inject constructor(
    private val observeOcupacionUseCase: ObserveOcupacionUseCase,
    private val deleteOcupacionUseCase: DeleteOcupacionUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ListOcupacionUiState(isLoading = true))
    val state: StateFlow<ListOcupacionUiState> = _state.asStateFlow()

    init {
        onEvent(ListOcupacionUiEvent.Load)
    }

    fun onEvent(event: ListOcupacionUiEvent) {
        when (event) {
            ListOcupacionUiEvent.Load -> observeOcupaciones()
            is ListOcupacionUiEvent.Delete -> onDelete(event.ocupacion)
            ListOcupacionUiEvent.CreateNew -> _state.update { it.copy(navigateToCreate = true) }
            is ListOcupacionUiEvent.Edit -> _state.update { it.copy(navigateToEditId = event.id) }
            is ListOcupacionUiEvent.ShowMessage -> _state.update { it.copy(message = event.message) }
        }
    }

    private fun observeOcupaciones() {
        viewModelScope.launch {
            observeOcupacionUseCase().collectLatest { lista ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        ocupaciones = lista,
                        message = null
                    )
                }
            }
        }
    }

    private fun onDelete(ocupacion: Ocupacion) {
        viewModelScope.launch {
            try {
                deleteOcupacionUseCase(ocupacion)
                onEvent(ListOcupacionUiEvent.ShowMessage("Ocupación eliminada correctamente"))
            } catch (e: Exception) {
                onEvent(ListOcupacionUiEvent.ShowMessage("Error al eliminar: ${e.message}"))
            }
        }
    }

    }