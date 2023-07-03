package br.com.lambdateam.mycar.views.maintenance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.lambdateam.mycar.model.maintenance.Maintenance
import br.com.lambdateam.mycar.model.maintenance.MaintenancePresentModel
import br.com.lambdateam.mycar.model.utils.ViewState
import br.com.lambdateam.mycar.model.utils.convertDateFormat
import br.com.lambdateam.mycar.model.utils.convertToCurrencyFormat
import br.com.lambdateam.mycar.network.ApiRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class MaintenanceViewModel(private val repository: ApiRepository) : ViewModel() {

    private val _maintenancesResponse = MutableLiveData<List<Maintenance>>()
    val maintenancesResponse: LiveData<List<Maintenance>>
        get() = _maintenancesResponse

    private val _maintenances = MutableLiveData<List<MaintenancePresentModel>>()
    val maintenances: LiveData<List<MaintenancePresentModel>>
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
                response.body()?.map {
                    MaintenancePresentModel(
                        km = StringBuilder(it.km.toString()).append("KM").toString(),
                        maintenanceDate = convertDateFormat(it.maintenanceDate.toString()),
                        nextKm = null,
                        amount = convertToCurrencyFormat(it.amount ?: .0),
                        manufacturer = it.manufacturer?.manufacturer,
                        vehicle = it.vehicle?.description,
                        component = it.component?.component,
                        maintenanceType = it.maintenanceType?.maintenanceType
                    )
                }?.let {
                    _maintenances.postValue(it)
                }
            }

            else -> {
                _viewState.postValue(ViewState.Error)
            }
        }
    }
}