package com.example.prviewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider



class MainActivityViewModelProviderFactory(
    val estilos: Array<String> //Y el resto de parámetros necesarios
) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        //Aquí se invoca al constructor con los parámetros necesarios
        return MainActivityViewModel(estilos) as T
    }
}

class MainActivityViewModel(
    private val _estilos: Array<String>
) : ViewModel() {


    private val _avatarUIState by lazy {

        val avatarUIState = AvatarUIState(
            avatar = Avatar("",""),
            esFavorito = false,
            imgURL = ""
        )
        MutableLiveData(avatarUIState)
    }

    val avatarUIState : LiveData<AvatarUIState>
            get() = _avatarUIState


    fun generarAvatar(identificador : String, estilo : String) {
        val avatar = Avatar(identificador, estilo)
        val imgURL = "https://api.dicebear.com/9.x/${estilo}/svg?seed=${identificador}"
        val esFavorito = _avatarUIState.value!!.listaFavs.contains(avatar)
        _avatarUIState.value = _avatarUIState.value!!.copy(
            avatar = avatar,
            imgURL = imgURL,
            esFavorito = esFavorito )

        Log.d("PR9", _avatarUIState.value!!.toString())
    }

    fun cambiarFavorito(esFavorito: Boolean) {
        val avatar = _avatarUIState.value!!.avatar
        val listaFavs = _avatarUIState.value!!.listaFavs.toMutableList()
        if (esFavorito) listaFavs.add(avatar) else listaFavs.remove(avatar)

        _avatarUIState.value = _avatarUIState.value!!.copy(
            listaFavs = listaFavs,
            esFavorito =  esFavorito
        )
    }

 /*   private val _url = MutableLiveData("")
    val url : LiveData<String>
        get() = _url

    private val _esFavorito = MutableLiveData(false)
    val esFavorito : LiveData<Boolean>
        get() = _esFavorito

    private val _listaFavs = mutableListOf<Avatar>()

    private val _identificador = MutableLiveData("")
    val identificador : LiveData<String>
        get() = _identificador

    private val _estiloIndex = MutableLiveData(0)

    val estiloIndex : LiveData<Int>
        get() = _estiloIndex

    fun generarAvatar() {
        val avatar = Avatar(_identificador.value!!, _estilos[_estiloIndex.value!!])
        _url.value = "https://api.dicebear.com/9.x/${_estilos[_estiloIndex.value!!]}/svg?seed=${_identificador.value}"
        _esFavorito.value = _listaFavs.contains(avatar)
    }

    fun guardarIdentificador(newIdentificador : String) {
        _identificador.value = newIdentificador
    }

    fun guardarEstilo(newEstiloIndex:Int) {
        _estiloIndex.value = newEstiloIndex
    }


    fun gestionarFav(isChecked:Boolean) {
        val avatar = Avatar(_identificador.value!!, _estilos[_estiloIndex.value!!])
        if (isChecked) _listaFavs.add(avatar) else _listaFavs.remove(avatar)
        _esFavorito.value = isChecked
    }*/




}