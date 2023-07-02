package br.com.lambdateam.mycar.model.maintenance

data class Maintenance(
    val id: Int?,
    val km: Double?,
    val maintenanceDate: String?,
    val nextKm: Double?,
    val amount: Double?,
    val manufacturer: MaintenanceManufacturer?,
    val vehicle: MaintenanceVehicle?,
    val component: MaintenanceComponent?,
    val maintenanceType: MaintenanceType?,
)
