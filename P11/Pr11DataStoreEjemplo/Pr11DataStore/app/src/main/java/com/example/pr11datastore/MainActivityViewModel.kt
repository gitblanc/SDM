package com.example.pr11datastore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainActivityViewModelProviderFactory(
    private val preferenciasDataStore: PreferenciasDataStore
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainActivityViewModel(preferenciasDataStore) as T
    }
}

class MainActivityViewModel(private val preferenciasDataStore: PreferenciasDataStore) :
    ViewModel() {
    //Así para evitar el init
//    val color = preferenciasDataStore.color.asLiveData()
//    val edad = preferenciasDataStore.edad.asLiveData()
    val preferenciasDataClass = preferenciasDataStore.preferenciasDataClass

//    fun guardarColor(color: String) {
//        viewModelScope.launch {
//            preferenciasDataStore.guardarColor(color)
//        }
//    }
//
//    fun guardarEdad(edad: Int) {
//        viewModelScope.launch {
//            preferenciasDataStore.guardarEdad(edad)
//        }
//    }

    fun guardarPreferencias(color: String, edad: Int) {
        viewModelScope.launch {
            preferenciasDataStore.guardarPreferencias(color, edad)
        }
    }
}