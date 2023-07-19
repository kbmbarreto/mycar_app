package br.com.lambdateam.mycar.views.maintenance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.lambdateam.mycar.model.maintenance.MaintenancePresentModel
import br.com.lambdateam.mycar.model.utils.ViewState
import br.com.lambdateam.mycar.network.ApiRepository
import kotlinx.coroutines.launch

class MaintenanceDetailViewModel(private val repository: ApiRepository) : ViewModel() {

    private val _maintenance = MutableLiveData<MaintenancePresentModel>()
    val maintenance: LiveData<MaintenancePresentModel>
        get() = _maintenance

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState>
        get() = _viewState

    private val _isSuccessDelete = MutableLiveData<Boolean>()
    val isSuccessDelete: LiveData<Boolean>
        get() = _isSuccessDelete

    fun setMaintenance(maintenance: MaintenancePresentModel) {
        _maintenance.value = maintenance
    }

    fun deleteMaintenance() {
        _viewState.value = ViewState.Loading
        _maintenance.value?.id?.let {
            viewModelScope.launch {
                repository.deleteMaintenance(it.toString()).let { response ->
                    when {
                        response.isSuccessful && response.body() != null -> {
                            _viewState.postValue(ViewState.HideLoading)
                            _isSuccessDelete.postValue(true)
                        }

                        else -> _viewState.postValue(ViewState.Error)
                    }
                }
            }
        }
    }
}