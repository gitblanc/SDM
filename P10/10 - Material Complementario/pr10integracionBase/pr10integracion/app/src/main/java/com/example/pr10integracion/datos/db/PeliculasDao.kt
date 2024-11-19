package com.example.pr10integracion.datos.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.example.pr10integracion.datos.db.entities.PeliculaEntity

@Dao
interface PeliculasDao {
    @Query("SELECT * FROM peliculas")
    suspend fun getAll(): List<PeliculaEntity>

    @Query("SELECT * FROM peliculas WHERE id = (:movieId)")
    suspend fun findById(movieId: Int): PeliculaEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(movie: PeliculaEntity)

    @Delete
    suspend fun delete(movie : PeliculaEntity)

    @Query("DELETE FROM peliculas")
    suspend fun deleteAll()

}