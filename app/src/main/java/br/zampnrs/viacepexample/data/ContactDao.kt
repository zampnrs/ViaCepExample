package br.zampnrs.viacepexample.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ContactDao {

    @Query("SELECT * FROM contact")
    fun getAll(): List<ContactEntity>

    @Insert
    fun insertAll(vararg contacts: ContactEntity)

    @Delete
    fun delete(contact: ContactEntity)
}