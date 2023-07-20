package br.com.lambdateam.mycar.views.maintenance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.lambdateam.mycar.model.maintenance.MaintenancePresentModel
import br.com.lambdateam.mycar.model.utils.ViewState
import br.com.lambdateam.mycar.model.utils.convertDateFormat
import br.com.lambdateam.mycar.model.utils.convertToCurrencyFormat
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

                        else -> _viewState.postValue(ViewState.Error())
                    }
                }
            }
        }
    }

    fun fetchMaintenanceDetail() {
        _viewState.value = ViewState.Loading
        _maintenance.value?.id?.let {
            viewModelScope.launch {
                repository.getMaintenanceById(it.toString()).let { response ->
                    when {
                        response.isSuccessful && response.body() != null -> {
                            _viewState.postValue(ViewState.HideLoading)
                            _maintenance.postValue(response.body()?.let {
                                MaintenancePresentModel(
                                    id = it.id,
                                    km = StringBuilder(it.km.toString()).append("KM").toString(),
                                    maintenanceDate = convertDateFormat(it.maintenanceDate.toString()),
                                    nextKm = StringBuilder(it.nextKm.toString()).append("KM")
                                        .toString(),
                                    amount = convertToCurrencyFormat(it.amount ?: .0),
                                    manufacturer = it.manufacturer?.manufacturer,
                                    vehicle = it.vehicle?.description,
                                    component = it.component?.component,
                                    maintenanceType = it.maintenanceType?.maintenanceType
                                )
                            })
                        }

                        else -> _viewState.postValue(ViewState.Error("Erro ao atualizar dados da manutenção"))
                    }
                }
            }
        }
    }
}