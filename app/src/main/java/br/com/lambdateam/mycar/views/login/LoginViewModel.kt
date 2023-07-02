package br.com.lambdateam.mycar.views.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.lambdateam.mycar.model.login.LoginDTO
import br.com.lambdateam.mycar.model.login.LoginResponse
import br.com.lambdateam.mycar.model.utils.ViewState
import br.com.lambdateam.mycar.network.ApiRepository
import br.com.lambdateam.mycar.sharedpreferences.UserSession
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(private val repository: ApiRepository, private val userSession: UserSession) :
    ViewModel() {

    private val _email = MutableLiveData<String>()
    private val _password = MutableLiveData<String>()

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse>
        get() = _loginResponse

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState>
        get() = _viewState

    private val _isUserAuthenticated = MutableLiveData<Boolean>()
    val isUserAuthenticated: LiveData<Boolean>
        get() = _isUserAuthenticated

    init {
        val token = userSession.getToken()
        _isUserAuthenticated.value = !token.isNullOrEmpty()
    }

    fun login() {
        _viewState.value = ViewState.Loading
        viewModelScope.launch {
            afterLogin(repository.login(LoginDTO(_email.value ?: "", _password.value ?: "")))
        }
    }

    private fun afterLogin(response: Response<LoginResponse>) {
        _viewState.postValue(ViewState.HideLoading)
        when {
            response.isSuccessful && response.body()?.token != null -> {
                userSession.setToken(response.body()!!.token!!)
                _loginResponse.postValue(response.body())
            }

            else -> {
                _viewState.postValue(ViewState.Error)
            }
        }
    }

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }
}