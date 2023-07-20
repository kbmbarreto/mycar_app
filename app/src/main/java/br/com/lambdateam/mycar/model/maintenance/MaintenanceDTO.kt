package br.com.lambdateam.mycar.model.maintenance

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MaintenanceDTO(
    var km: Double,
    var maintenanceDate: String,
    var nextKm: Double,
    var amount: Double,
    var manufacturer: IdModel,
    var vehicle: IdModel,
    var component: IdModel,
    var maintenanceType: IdModel,
    val id: Int? = null
): Parcelable
