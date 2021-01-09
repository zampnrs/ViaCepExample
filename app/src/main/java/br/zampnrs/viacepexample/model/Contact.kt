package br.zampnrs.viacepexample.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact (
        val uuid: String,
        val name: String,
        val email: String,
        val phone: String,
        val cep: String,
        val street: String,
        val number: String,
        val complement: String,
        val city: String,
        val uf: String
): Parcelable