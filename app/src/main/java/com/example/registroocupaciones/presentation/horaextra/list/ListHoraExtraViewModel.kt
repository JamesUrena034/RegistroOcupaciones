package com.example.registroocupaciones.presentation.horaextra.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registroocupaciones.domain.empleados.usecase.ObserveEmpleadoUseCase
import com.example.registroocupaciones.domain.horaextra.usecase.ObserveHorasExtrasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListHoraExtraViewModel @Inject constructor(
    private val observeHorasExtrasUseCase: ObserveHorasExtrasUseCase,
    private val observeEmpleadoUseCase: ObserveEmpleadoUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ListHoraExtraUiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            combine(
                observeHorasExtrasUseCase(),
                observeEmpleadoUseCase()
            ) { horasExtras, listaEmpleados ->
                _state.update {
                    it.copy(
                        registros = horasExtras,
                        empleados = listaEmpleados,
                        isLoading = false
                    )
                }
            }.collect {}
        }
    }
}