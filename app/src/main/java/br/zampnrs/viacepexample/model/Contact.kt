package br.zampnrs.viacepexample.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact (
        val uuid: String,
        val name: String,
        val email: String,
        val phone: String
): Parcelable