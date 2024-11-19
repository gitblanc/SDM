package com.example.pr10integracion.datos.modelo

import com.google.gson.annotations.SerializedName

data class MovieCreditsResult (
    @SerializedName("id")
    val id : Int,

    @SerializedName("cast")
    val reparto : List<Interprete>,

    @SerializedName("crew")
    val personal : List<CrewMember>
)