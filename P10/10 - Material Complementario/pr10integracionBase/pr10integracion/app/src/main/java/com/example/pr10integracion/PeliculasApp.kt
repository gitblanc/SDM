package com.example.pr10integracion

import android.app.Application
import android.content.Context
import com.example.pr10integracion.datos.PeliculasRepository
import com.example.pr10integracion.datos.db.PeliculasDb
import com.example.pr10integracion.datos.network.TheMovieDbApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PeliculasApp : Application() {

    // Es IMPORTANTE añadir esta clase al manifiesto: android:name=".PeliculasApp"

    companion object {
        lateinit var appModule: AppModule
        val moviesApiKey = "6bc4475805ebbc4296bcfa515aa8df08"
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)

    }
}

//¿Por qué utilizamos una interfaz?
interface AppModule {
    val peliculasDb : PeliculasDb
    val moviesApi : TheMovieDbApi
    val peliculasRepository : PeliculasRepository
}


class AppModuleImpl(
    private val contexto : Context
) : AppModule {


    //ROOM
    override val peliculasDb: PeliculasDb by lazy {
        PeliculasDb.getDB(contexto)
    }

    //Retrofit
    override val moviesApi: TheMovieDbApi by lazy {
        val logging  = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient : OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
            .create(TheMovieDbApi::class.java)
    }

    //Repositorio
    override val peliculasRepository: PeliculasRepository by lazy {
        PeliculasRepository(peliculasDb, moviesApi)
    }

}