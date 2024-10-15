package com.example.pr5corrutinasroom

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "contactos")
data class Contacto(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val nombre:String,
    val apellido:String,
    val telefono:String,
    @ColumnInfo(name = "imagen_url")
    val imagenURL:String) : Parcelable


