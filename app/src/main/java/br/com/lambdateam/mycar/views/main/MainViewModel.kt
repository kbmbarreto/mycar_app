package br.com.lambdateam.mycar.views.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.lambdateam.mycar.model.utils.ViewState
import br.com.lambdateam.mycar.model.utils.delay
import br.com.lambdateam.mycar.sharedpreferences.UserSession

class MainViewModel(private val userSession: UserSession): ViewModel() {

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState>
        get() = _viewState

    fun logOut() {
        userSession.setToken(null)
    }
}