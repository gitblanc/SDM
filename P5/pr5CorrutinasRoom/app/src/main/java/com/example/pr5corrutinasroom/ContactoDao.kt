package com.example.pr5corrutinasroom

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ContactoDao {
    @Query("SELECT * FROM contactos WHERE id = (:contactoId)")
    suspend fun findById(contactoId: Int): Contacto

    @Insert
    suspend fun insertContact(contacto: Contacto)

    @Update
    suspend fun updateContact(contacto: Contacto): Int

    @Delete
    suspend fun deleteContact(contacto: Contacto): Int

    @Query("SELECT * FROM contactos")
    suspend fun getAll(): List<Contacto>
}