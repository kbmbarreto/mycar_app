package br.com.lambdateam.mycar.model.utils

sealed class ViewState {
    object Loading : ViewState()
    data class Error(val message: String? = null) : ViewState()
    object Empty : ViewState()
    object HideLoading : ViewState()
}
