package com.example.pr10integracion.datos.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="peliculas")
data class PeliculaEntity (
    @PrimaryKey(autoGenerate = false)
    val id :Int,
    val titulo: String,
    @ColumnInfo(name = "fecha_estreno")
    val fechaEstreno : String,
    val argumento: String,
    @ColumnInfo(name = "url_caratula")
    val urlCaratula : String,
    @ColumnInfo(name = "url_fondo")
    val urlFondo: String,
)