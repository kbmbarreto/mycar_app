package br.com.lambdateam.mycar.model.maintenance

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class IdModel(
    var id: Int
) : Parcelable