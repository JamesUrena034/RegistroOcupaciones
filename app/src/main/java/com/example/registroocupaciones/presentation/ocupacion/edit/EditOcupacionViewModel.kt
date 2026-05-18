package com.example.registroocupaciones.presentation.ocupacion.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registroocupaciones.domain.ocupaciones.model.Ocupacion
import com.example.registroocupaciones.domain.ocupaciones.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditOcupacionViewModel @Inject constructor(
    private val getOcupacionUseCase: GetOcupacionUseCase,
    private val upsertOcupacionUseCase: UpsertOcupacionUseCase,
    private val deleteOcupacionUseCase: DeleteOcupacionUseCase,
    private val validateOcupacionUseCase: ValidateOcupacionUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(EditOcupacionUiState())
    val state = _state.asStateFlow()

    fun onEvent(event: EditOcupacionUiEvent) {
        when (event) {
            is EditOcupacionUiEvent.Load -> onLoad(event.id)
            is EditOcupacionUiEvent.DescripcionChanged -> {
                _state.update { it.copy(descripcion = event.value, descripcionError = null) }
            }
            is EditOcupacionUiEvent.SueldoChanged -> {
                _state.update { it.copy(sueldo = event.value, sueldoError = null) }
            }
            EditOcupacionUiEvent.Save -> onSave()
            EditOcupacionUiEvent.Delete -> onDelete()
        }
    }

    private fun onLoad(id: Int?) {
        if (id == null || id == 0) return
        viewModelScope.launch {
            getOcupacionUseCase(id)?.let { ocupacion ->
                _state.update {
                    it.copy(
                        isNew = false,
                        ocupacionId = ocupacion.ocupacionId,
                        descripcion = ocupacion.descripcion,
                        sueldo = ocupacion.sueldo.toString()
                    )
                }
            }
        }
    }

    private fun onSave() {
        viewModelScope.launch {
            val sueldoDouble = _state.value.sueldo.toDoubleOrNull()

            // Validar antes de guardar
            val validation = validateOcupacionUseCase(
                descripcion = _state.value.descripcion,
                sueldo = sueldoDouble,
                currentId = _state.value.ocupacionId
            )

            if (!validation.isValid) {
                _state.update { it.copy(
                    descripcionError = validation.descripcionError,
                    sueldoError = validation.sueldoError
                )}
                return@launch
            }

            _state.update { it.copy(isSaving = true) }

            val ocupacion = Ocupacion(
                ocupacionId = _state.value.ocupacionId,
                descripcion = _state.value.descripcion,
                sueldo = sueldoDouble ?: 0.0
            )

            upsertOcupacionUseCase(ocupacion).onSuccess {
                _state.update { it.copy(isSaving = false, saved = true) }
            }.onFailure { e ->
                _state.update { it.copy(isSaving = false, descripcionError = e.message) }
            }
        }
    }

    private fun onDelete() {
        viewModelScope.launch {
            val sueldoDouble = _state.value.sueldo.toDoubleOrNull() ?: 0.0
            val ocupacion = Ocupacion(
                ocupacionId = _state.value.ocupacionId,
                descripcion = _state.value.descripcion,
                sueldo = sueldoDouble
            )
            _state.update { it.copy(isDeleting = true) }
            deleteOcupacionUseCase(ocupacion)
            _state.update { it.copy(isDeleting = false, deleted = true) }
        }
    }
}