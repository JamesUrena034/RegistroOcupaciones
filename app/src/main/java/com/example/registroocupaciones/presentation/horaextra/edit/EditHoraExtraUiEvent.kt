package com.example.registroocupaciones.presentation.horaextra.edit

import com.example.registroocupaciones.domain.empleadoos.model.Empleado

sealed interface EditHoraExtraUiEvent {
    data class Load(val id: Int?) : EditHoraExtraUiEvent
    data class EmpleadoChanged(val empleado: Empleado) : EditHoraExtraUiEvent
    data class HorasSemanalesChanged(val value: String) : EditHoraExtraUiEvent
    data class HorasNocturnasChanged(val value: String) : EditHoraExtraUiEvent
    object Save : EditHoraExtraUiEvent
    object Delete : EditHoraExtraUiEvent
}