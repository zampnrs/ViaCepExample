package br.zampnrs.viacepexample.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
data class ContactEntity(
    @PrimaryKey val uuid: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "cep") val cep: String,
    @ColumnInfo(name = "street") val street: String,
    @ColumnInfo(name = "number") val number: String,
    @ColumnInfo(name = "complement") val complement: String,
    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "uf") val uf: String
)