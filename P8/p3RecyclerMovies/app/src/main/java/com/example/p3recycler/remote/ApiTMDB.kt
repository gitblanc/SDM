package com.example.p3recycler.remote

import com.example.p3recycler.remote.data.MovieDetail
import com.example.p3recycler.remote.data.MovieListData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiTMDB {
    // Aquí van todos los endpoints que necesitemos
    // Es importante importar retrofit2.Response, no la de okhttp3.Response

    // https://api.themoviedb.org/3/movie/popular?api_key=<codigo>&language=es-ES&page=1
    @GET("movie/{type}")
    // Cualquier cosa que llamemos desde un hilo secundario tenemos que definirlo como suspend
    suspend fun getMovieList(
        @Path("type") type: String,
        @Query("api_key") token: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<MovieListData>

    // https://api.themoviedb.org/3/movie/id?api_key=<codigo>&language=es-ES&page=1&append_to_response=videos
    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") id: Int,
        @Query("api_key") token: String,
        @Query("language") language: String,
        @Query("append_to_response") appResp: String,
    ): Response<MovieDetail>
}