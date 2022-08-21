package com.arnyminerz.games.la_pocha.game

import android.os.Parcel
import android.os.Parcelable

data class Player(
    val name: String,
) : Parcelable {
    val tag: String = name
        .takeIf { it.length >= 3 }
        ?.substring(0, 3)
        ?.uppercase()
        ?: "AAA"

    constructor(parcel: Parcel) : this(parcel.readString()!!)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }

    override fun describeContents(): Int = name.hashCode()

    companion object CREATOR : Parcelable.Creator<Player> {
        override fun createFromParcel(parcel: Parcel): Player =
            Player(parcel)

        override fun newArray(size: Int): Array<Player?> =
            arrayOfNulls(size)
    }
}