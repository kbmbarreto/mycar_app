package br.com.lambdateam.mycar.model.maintenance

data class Maintenance(
    val id: Int?,
    val km: Int?,
    val maintenanceDate: String?,
    val nextKm: Int?,
    val amount: Double?,
    val manufacturer: MaintenanceManufacturer?,
    val vehicle: MaintenanceVehicle?,
    val component: MaintenanceComponent?,
    val maintenanceType: MaintenanceType?,
)
