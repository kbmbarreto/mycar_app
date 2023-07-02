package br.com.lambdateam.mycar.model.utils

sealed class ViewState {
    object Loading: ViewState()
    object Error: ViewState()
    object Empty: ViewState()
    object HideLoading: ViewState()
}
