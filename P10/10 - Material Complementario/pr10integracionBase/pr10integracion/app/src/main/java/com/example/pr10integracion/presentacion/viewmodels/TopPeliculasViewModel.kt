package com.example.pr10integracion.presentacion.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.pr10integracion.datos.PeliculasRepository
import com.example.pr10integracion.datos.modelo.MovieListResult
import kotlinx.coroutines.launch
import retrofit2.Response

class TopPeliculasViewModel(private val peliculasRepository: PeliculasRepository) : ViewModel() {

    private val _favoritas = MutableLiveData<Response<MovieListResult>>()

    val favoritas: MutableLiveData<Response<MovieListResult>>
        get() = _favoritas

    fun updatePopularMovies() {
        viewModelScope.launch {
            _favoritas.postValue() = peliculasRepository.getPopularMovies()
        }
    }

}

class TopPeliculasViewModelProviderFactory(private val peliculasRepository: PeliculasRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TopPeliculasViewModel(peliculasRepository) as T
    }
}