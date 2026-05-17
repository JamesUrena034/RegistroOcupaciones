package com.example.registroocupaciones.presentation.empleado.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registroocupaciones.domain.empleados.usecase.ObserveEmpleadoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmpleadoListViewModel @Inject constructor(
    private val observeEmpleadoUseCase: ObserveEmpleadoUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(EmpleadoListUiState())
    val state = _state.asStateFlow()

    init {
        getEmpleados()
    }

    private fun getEmpleados() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            observeEmpleadoUseCase().collect { lista ->
                _state.update { it.copy(empleados = lista, isLoading = false) }
            }
        }
    }
}