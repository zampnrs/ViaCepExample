package br.zampnrs.viacepexample.data

import androidx.room.*

@Dao
interface ContactDao {

    @Query("SELECT * FROM contact")
    fun getAll(): List<ContactEntity>

    @Insert
    fun insert(vararg contacts: ContactEntity)

    @Delete
    fun delete(contact: ContactEntity)

    @Update
    fun update(contact: ContactEntity)
}