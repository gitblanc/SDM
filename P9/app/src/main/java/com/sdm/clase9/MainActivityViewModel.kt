package com.sdm.clase9

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {
    private val _numero = MutableLiveData(-1)

    val numero: LiveData<Int>
        get() = _numero

    fun generarAleatorio() {
        _numero.value = (0..10).random()
    }
}