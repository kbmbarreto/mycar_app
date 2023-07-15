package br.com.lambdateam.mycar.views.maintenance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.lambdateam.mycar.model.maintenance.IdModel
import br.com.lambdateam.mycar.model.maintenance.MaintenanceComponent
import br.com.lambdateam.mycar.model.maintenance.MaintenanceDTO
import br.com.lambdateam.mycar.model.maintenance.MaintenanceManufacturer
import br.com.lambdateam.mycar.model.maintenance.MaintenanceType
import br.com.lambdateam.mycar.model.maintenance.MaintenanceVehicle
import br.com.lambdateam.mycar.model.utils.ViewState
import br.com.lambdateam.mycar.network.ApiRepository
import kotlinx.coroutines.launch

class AddMaintenanceViewModel(private val repository: ApiRepository) : ViewModel() {

    private val _maintenanceTypes = MutableLiveData<List<MaintenanceType>>()
    val maintenanceTypes: LiveData<List<MaintenanceType>>
        get() = _maintenanceTypes

    private val _maintenanceVehicles = MutableLiveData<List<MaintenanceVehicle>>()
    val maintenanceVehicles: LiveData<List<MaintenanceVehicle>>
        get() = _maintenanceVehicles

    private val _maintenanceComponents = MutableLiveData<List<MaintenanceComponent>>()
    val maintenanceComponents: LiveData<List<MaintenanceComponent>>
        get() = _maintenanceComponents

    private val _maintenanceManufacturers = MutableLiveData<List<MaintenanceManufacturer>>()
    val maintenanceManufacturers: LiveData<List<MaintenanceManufacturer>>
        get() = _maintenanceManufacturers

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState>
        get() = _viewState

    private val _isButtonEnabled = MutableLiveData<Boolean>()
    val isButtonEnabled: LiveData<Boolean>
        get() = _isButtonEnabled

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean>
        get() = _isSuccess

    private val _km = MutableLiveData<Double?>()
    private val _nextKm = MutableLiveData<Double?>()
    private val _amount = MutableLiveData<Double?>()
    private val _type = MutableLiveData<Int?>()
    private val _vehicle = MutableLiveData<Int?>()
    private val _component = MutableLiveData<Int?>()
    private val _manufacturer = MutableLiveData<Int?>()
    private val _date = MutableLiveData<String?>()

    private var responseReturnCount = 0
    private var responseErrorCount = 0

    fun setDate(value: String) {
        onValueChanged {
            _date.value = value
        }
    }

    fun setKm(value: String) {
        onValueChanged {
            try {
                _km.value = value.toDouble()
            } catch (_: Exception) {
                _km.value = null
            }
        }
    }

    fun setNextKm(value: String) {
        onValueChanged {
            try {
                _nextKm.value = value.toDouble()
            } catch (_: Exception) {
                _nextKm.value = null
            }
        }
    }

    fun setAmount(value: String) {
        onValueChanged {
            try {
                _amount.value = value.toDouble()
            } catch (_: Exception) {
                _amount.value = null
            }
        }
    }

    fun setType(position: Int) {
        onValueChanged {
            try {
                _type.value = position
            } catch (_: Exception) {
                _type.value = null
            }
        }
    }

    fun setVehicle(position: Int) {
        onValueChanged {
            try {
                _vehicle.value = position
            } catch (_: Exception) {
                _vehicle.value = null
            }
        }
    }

    fun setComponent(position: Int) {
        onValueChanged {
            try {
                _component.value = position
            } catch (_: Exception) {
                _component.value = null
            }
        }
    }

    fun setManufacturer(position: Int) {
        onValueChanged {
            try {
                _manufacturer.value = position
            } catch (_: Exception) {
                _manufacturer.value = null
            }
        }
    }

    fun onViewLoaded() {
        _viewState.value = ViewState.Loading
        getMaintenanceTypes()
        getVehicles()
        getComponents()
        getManufacturers()
    }

    private fun onValueChanged(run: () -> Unit) {
        run()
        _isButtonEnabled.value = _km.value != null &&
                _nextKm.value != null &&
                _amount.value != null &&
                _type.value != null &&
                _vehicle.value != null &&
                _component.value != null &&
                _manufacturer.value != null &&
                _date.value != null

    }

    private fun setLoaded() {
        responseReturnCount++
        if (responseReturnCount == 4) {
            _viewState.postValue(ViewState.HideLoading)
        }
    }

    private fun setError() {
        responseErrorCount++
    }

    private fun getComponents() {
        viewModelScope.launch {
            repository.getComponents().let { response ->
                when {
                    response.isSuccessful && response.body() != null -> {
                        setLoaded()
                        _maintenanceComponents.postValue(response.body())
                    }

                    else -> setError()
                }
            }
        }
    }

    private fun getVehicles() {
        viewModelScope.launch {
            repository.getVehicles().let { response ->
                when {
                    response.isSuccessful && response.body() != null -> {
                        setLoaded()
                        _maintenanceVehicles.postValue(response.body())
                    }

                    else -> setError()
                }
            }
        }
    }

    private fun getManufacturers() {
        viewModelScope.launch {
            repository.getManufacturers().let { response ->
                when {
                    response.isSuccessful && response.body() != null -> {
                        setLoaded()
                        _maintenanceManufacturers.postValue(response.body())
                    }

                    else -> setError()
                }
            }
        }
    }

    private fun getMaintenanceTypes() {
        viewModelScope.launch {
            repository.getMaintenanceTypes().let { response ->
                when {
                    response.isSuccessful && response.body() != null -> {
                        setLoaded()
                        _maintenanceTypes.postValue(response.body())
                    }

                    else -> setError()
                }
            }
        }
    }

    fun createMaintenance() {
        _viewState.value = ViewState.Loading
        val maintenance = MaintenanceDTO(
            _km.value ?: .0,
            _date.value ?: "",
            _nextKm.value ?: .0,
            _amount.value ?: .0,
            IdModel(_manufacturer.value?.or(0)?.plus(1) ?: 0),
            IdModel(_vehicle.value?.or(0)?.plus(1) ?: 0),
            IdModel(_component.value?.or(0)?.plus(1) ?: 0),
            IdModel(_type.value?.or(0)?.plus(1) ?: 0)
        )
        viewModelScope.launch {
            repository.createMaintenance(maintenance).let { response ->
                when {
                    response.isSuccessful && response.body() != null -> {
                        _viewState.postValue(ViewState.HideLoading)
                        _isSuccess.postValue(true)
                    }

                    else -> _viewState.postValue(ViewState.Error)
                }
            }
        }
    }
}