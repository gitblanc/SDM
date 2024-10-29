package com.example.p3recycler

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pelicula(
    val id: Int,
    val titulo: String,
    val argumento: String,
    val categoria: String,
    val duracion: Int,
    val fecha: String,
    val urlCaratula: String,
    val urlFondo: String,
    val urlTrailer: String
): Parcelable

