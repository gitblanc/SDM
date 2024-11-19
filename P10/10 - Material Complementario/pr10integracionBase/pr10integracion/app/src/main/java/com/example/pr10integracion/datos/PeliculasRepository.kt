package com.example.pr10integracion.datos

import com.example.pr10integracion.datos.db.PeliculasDb
import com.example.pr10integracion.datos.db.entities.PeliculaEntity
import com.example.pr10integracion.datos.modelo.MovieListResult
import com.example.pr10integracion.datos.modelo.MovieResponse
import com.example.pr10integracion.datos.network.TheMovieDbApi
import retrofit2.Response

class PeliculasRepository(peliculasDb: PeliculasDb, moviesApi: TheMovieDbApi) {
    private val peliculasDb = peliculasDb
    private val moviesApi = moviesApi

    // Método para obtener las películas populares
    suspend fun getPopularMovies(): Response<MovieListResult> {
        return moviesApi.listMovies(
            "popular",
            "6bc4475805ebbc4296bcfa515aa8df08",
            "es-ES",
            1
        )
    }

    // Método para obtener una película por su id
    suspend fun getMovie(id: Int): Response<MovieResponse> {
        return moviesApi.getMovie(
            id,
            "6bc4475805ebbc4296bcfa515aa8df08",
            "es-ES"
        )
    }

    // Método para insertar una peliculaEntity a favoritas
    suspend fun insertFavorita(peliculaEntity: PeliculaEntity) {
        peliculasDb.peliculasDao.add(peliculaEntity)
    }

    // Método para eliminar una peliculaEntity de favoritas
    suspend fun deleteFavorita(peliculaEntity: PeliculaEntity) {
        peliculasDb.peliculasDao.delete(peliculaEntity)
    }

    // Método para obtener todas las peliculasEntity favoritas
    suspend fun getFavoritas(): List<PeliculaEntity> {
        return peliculasDb.peliculasDao.getAll()
    }
}