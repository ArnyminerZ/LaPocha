package com.arnyminerz.games.la_pocha.game

import android.os.Parcel
import android.os.Parcelable

data class GameInfo(
    val players: List<Player>,
) : Parcelable {
    override fun describeContents(): Int =
        players.hashCode()

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeTypedList(players)
    }

    companion object CREATOR : Parcelable.Creator<GameInfo> {
        override fun createFromParcel(source: Parcel): GameInfo =
            GameInfo(
                arrayListOf<Player>().apply {
                    source.readTypedList(this, Player.CREATOR)
                }
            )

        override fun newArray(size: Int): Array<GameInfo?> =
            arrayOfNulls(size)
    }
}
