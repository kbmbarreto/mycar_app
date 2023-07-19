package br.com.lambdateam.mycar.model.maintenance

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MaintenancePresentModel(
    val id: Int?,
    val km: String?,
    val maintenanceDate: String?,
    val nextKm: String?,
    val amount: String?,
    val manufacturer: String?,
    val vehicle: String?,
    val component: String?,
    val maintenanceType: String?,
) : Parcelable
