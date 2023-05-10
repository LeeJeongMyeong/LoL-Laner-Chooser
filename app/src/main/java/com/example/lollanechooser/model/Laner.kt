package com.example.lollanechooser.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Laner
 * Created by 2023/05/10
 *
 * Description: 각 라이너 매칭 이름
 */
data class Laner(
    var topLaner: String? = "",
    var jglLaner: String? = "",
    var midLaner: String? = "",
    var botLaner: String? = "",
    var sptLaner: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun describeContents(): Int = 0

    override fun writeToParcel(p0: Parcel, p1: Int) {
        p0.writeString(topLaner)
        p0.writeString(jglLaner)
        p0.writeString(midLaner)
        p0.writeString(botLaner)
        p0.writeString(sptLaner)

    }

    companion object CREATOR : Parcelable.Creator<Laner> {
        override fun createFromParcel(parcel: Parcel): Laner {
            return Laner(parcel)
        }

        override fun newArray(size: Int): Array<Laner?> {
            return arrayOfNulls(size)
        }
    }

}
