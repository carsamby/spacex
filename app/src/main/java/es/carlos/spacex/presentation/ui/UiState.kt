package es.carlos.spacex.presentation.ui

data class UiState<T>(
    val data: T? = null,
    val isSuccess: Boolean? = null,
    val isLoading: Boolean? = null,
    val error: String? = null
)