package com.example.registroocupaciones.presentation.list


import com.example.registroocupaciones.domain.ocupaciones.model.Ocupacion

data class ListOcupacionUiState(
    val isLoading: Boolean = false,
    val ocupaciones: List<Ocupacion> = emptyList(),
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null
)