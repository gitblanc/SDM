package com.example.prviewmodel

data class Avatar(val identificador: String, val estilo : String)


data class AvatarUIState(
    val avatar : Avatar,
    val esFavorito : Boolean,
    val imgURL : String,
    val listaFavs : List<Avatar> = emptyList()
    )
