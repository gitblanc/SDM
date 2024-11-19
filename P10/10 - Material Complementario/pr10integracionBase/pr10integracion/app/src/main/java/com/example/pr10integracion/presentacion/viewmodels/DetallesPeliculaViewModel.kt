package com.example.pr10integracion.presentacion.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.pr10integracion.datos.PeliculasRepository
import com.example.pr10integracion.datos.db.entities.PeliculaEntity
import com.example.pr10integracion.datos.modelo.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class DetallesPeliculaViewModel(private val peliculasRepository: PeliculasRepository) :
    ViewModel() {

//    fun getMovie(id: Int): Response<MovieResponse> {
//        // Lanzo una corutina
////        viewModelScope.launch(Dispatchers.IO) {
////            return peliculasRepository.getMovie(id)
//        }

//    fun getFavoritas(): List<PeliculaEntity> {
//        return peliculasRepository.getFavoritas()
//    }

}

class DetallesPeliculaViewModelProviderFactory(private val peliculasRepository: PeliculasRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetallesPeliculaViewModel(peliculasRepository) as T
    }
}