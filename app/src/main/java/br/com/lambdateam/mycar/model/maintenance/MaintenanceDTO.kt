package br.com.lambdateam.mycar.model.maintenance

data class MaintenanceDTO(
    var km: Double,
    var maintenanceDate: String,
    var nextKm: Double,
    var amount: Double,
    var manufacturer: IdModel,
    var vehicle: IdModel,
    var component: IdModel,
    var maintenanceType: IdModel,
)
