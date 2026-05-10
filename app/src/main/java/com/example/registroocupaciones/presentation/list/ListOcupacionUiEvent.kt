package com.example.registroocupaciones.presentation.list

import com.example.registroocupaciones.domain.ocupaciones.model.Ocupacion

sealed interface ListOcupacionUiEvent {
    data object Load : ListOcupacionUiEvent
    data class Delete(val ocupacion: Ocupacion) : ListOcupacionUiEvent
    data object CreateNew : ListOcupacionUiEvent
    data class Edit(val id: Int) : ListOcupacionUiEvent
    data class ShowMessage(val message: String) : ListOcupacionUiEvent
}