package com.example.pr5corrutinasroom

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Contacto(
    val nombre:String,
    val apellido:String,
    val telefono:String,
    val imagenURL:String) : Parcelable


