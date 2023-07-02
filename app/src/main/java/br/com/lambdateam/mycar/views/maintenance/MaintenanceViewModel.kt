package br.com.lambdateam.mycar.views.maintenance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.lambdateam.mycar.model.maintenance.Maintenance
import br.com.lambdateam.mycar.model.utils.ViewState
import br.com.lambdateam.mycar.network.ApiRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class MaintenanceViewModel(private val repository: ApiRepository) : ViewModel() {

    private val _maintenances = MutableLiveData<List<Maintenance>>()
    val maintenances: LiveData<List<Maintenance>>
        get() = _maintenances

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState>
        get() = _viewState

    fun getMaintenances() {
        if (_maintenances.value == null) {
            _viewState.value = ViewState.Loading
            viewModelScope.launch {
                afterGetMaintenance(repository.getMaintenanceWithDetails())
            }
        }
    }

    private fun afterGetMaintenance(response: Response<List<Maintenance>>) {
        _viewState.postValue(ViewState.HideLoading)
        when {
            response.isSuccessful && response.body() != null -> {
                _viewState.postValue(
                    if (response.body()!!.isEmpty()) {
                        ViewState.Empty
                    } else {
                        ViewState.HideLoading
                    }
                )
                _maintenances.postValue(response.body())
            }

            else -> {
                _viewState.postValue(ViewState.Error)
            }
        }
    }
}