package com.example.registroocupaciones.presentation.empleado.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registroocupaciones.domain.empleadoos.model.Empleado
import com.example.registroocupaciones.domain.empleadoos.usecase.UpsertEmpleadoUseCase
import com.example.registroocupaciones.domain.empleados.usecase.GetEmpleadoUseCase
import com.example.registroocupaciones.domain.empleados.usecase.DeleteEmpleadoUseCase
import com.example.registroocupaciones.domain.empleados.usecase.ValidateEmpleadoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditEmpleadoViewModel @Inject constructor(
    private val getEmpleadoUseCase: GetEmpleadoUseCase,
    private val upsertEmpleadoUseCase: UpsertEmpleadoUseCase,
    private val deleteEmpleadoUseCase: DeleteEmpleadoUseCase,
    private val validateEmpleadoUseCase: ValidateEmpleadoUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(EditEmpleadoUiState())
    val state = _state.asStateFlow()

    fun onEvent(event: EditEmpleadoUiEvent) {
        when (event) {
            is EditEmpleadoUiEvent.Load -> onLoad(event.id)
            is EditEmpleadoUiEvent.NombresChanged -> {
                _state.update { it.copy(nombres = event.value, nombresError = null) }
            }
            is EditEmpleadoUiEvent.FechaIngresoChanged -> {
                _state.update { it.copy(fechaIngreso = event.value, fechaIngresoError = null) }
            }
            is EditEmpleadoUiEvent.SexoChanged -> {
                _state.update { it.copy(sexo = event.value, sexoError = null) }
            }
            is EditEmpleadoUiEvent.SueldoChanged -> {
                _state.update { it.copy(sueldo = event.value, sueldoError = null) }
            }
            EditEmpleadoUiEvent.Save -> onSave()
            EditEmpleadoUiEvent.Delete -> onDelete()
        }
    }

    private fun onLoad(id: Int?) {
        if (id == null || id == 0) return
        viewModelScope.launch {
            getEmpleadoUseCase(id)?.let { empleado ->
                _state.update {
                    it.copy(
                        isNew = false,
                        empleadoId = empleado.empleadoId,
                        nombres = empleado.nombres,
                        fechaIngreso = empleado.fechaIngreso,
                        sexo = empleado.sexo,
                        sueldo = empleado.sueldo.toString()
                    )
                }
            }
        }
    }

    private fun onSave() {
        viewModelScope.launch {
            val sueldoDouble = _state.value.sueldo.toDoubleOrNull()

            val validation = validateEmpleadoUseCase(
                nombres = _state.value.nombres,
                sueldo = sueldoDouble,
                fechaIngreso = _state.value.fechaIngreso,
                sexo = _state.value.sexo,
                currentId = _state.value.empleadoId
            )

            if (!validation.isValid) {
                _state.update { it.copy(
                    nombresError = validation.nombresError,
                    sueldoError = validation.sueldoError,
                    fechaIngresoError = validation.fechaError,
                    sexoError = validation.sexoError
                )}
                return@launch
            }

            _state.update { it.copy(isSaving = true) }

            val empleado = Empleado(
                empleadoId = _state.value.empleadoId,
                nombres = _state.value.nombres,
                fechaIngreso = _state.value.fechaIngreso,
                sexo = _state.value.sexo,
                sueldo = sueldoDouble ?: 0.0
            )

            upsertEmpleadoUseCase(empleado).onSuccess {
                _state.update { it.copy(isSaving = false, saved = true) }
            }.onFailure { e ->
                _state.update { it.copy(isSaving = false, nombresError = e.message) }
            }
        }
    }

    private fun onDelete() {
        viewModelScope.launch {
            val sueldoDouble = _state.value.sueldo.toDoubleOrNull() ?: 0.0
            val empleado = Empleado(
                empleadoId = _state.value.empleadoId,
                nombres = _state.value.nombres,
                fechaIngreso = _state.value.fechaIngreso,
                sexo = _state.value.sexo,
                sueldo = sueldoDouble
            )
            _state.update { it.copy(isDeleting = true) }
            deleteEmpleadoUseCase(empleado)
            _state.update { it.copy(isDeleting = false, deleted = true) }
        }
    }
}