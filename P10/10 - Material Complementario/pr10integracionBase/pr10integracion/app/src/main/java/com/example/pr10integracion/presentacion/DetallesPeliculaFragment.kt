package com.example.pr10integracion.presentacion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil3.load
import com.example.pr10integracion.PeliculasApp
import com.example.pr10integracion.R
import com.example.pr10integracion.datos.db.entities.PeliculaEntity
import com.example.pr10integracion.datos.modelo.MovieResponse
import com.example.pr10integracion.presentacion.viewmodels.DetallesPeliculaViewModel
import com.example.pr10integracion.presentacion.viewmodels.DetallesPeliculaViewModelProviderFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class DetallesPeliculaFragment : Fragment() {
    private val argumentos: DetallesPeliculaFragmentArgs by navArgs()
    private lateinit var checkFavorito: CheckBox
    private lateinit var pelicula: MovieResponse

    //    private lateinit var peliculasDb: PeliculasDb
//    private val peliculasDb by lazy { PeliculasDb.getDB(context) }
    private val peliculasApp = PeliculasApp
    private val peliculasRepository = peliculasApp.appModule.peliculasRepository

    private lateinit var viewModel: DetallesPeliculaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_detalles_pelicula, container, false)

//        peliculasDb = PeliculasDb.getDB(context)

        // Creamos el viewmodel
        viewModel = ViewModelProvider(
            this,
            DetallesPeliculaViewModelProviderFactory(peliculasRepository)
        ).get(DetallesPeliculaViewModel::class.java)

        val tvTitulo = root.findViewById<TextView>(R.id.tvTitulo)
        val ivPoster = root.findViewById<ImageView>(R.id.ivPoster)
        val tvArgumento = root.findViewById<TextView>(R.id.tvArgumento)
        checkFavorito = root.findViewById(R.id.ckbFavorito)
        checkFavorito.setOnClickListener { _ ->
            cambiarFavorita(checkFavorito.isChecked)
        }

        lifecycleScope.launch {
            pelicula = getPelicula(argumentos.id)
            withContext(Dispatchers.Main) {
                tvTitulo.text = pelicula.title
                tvArgumento.text = pelicula.overview
                ivPoster.load("https://image.tmdb.org/t/p/original/" + pelicula.posterPath)
                checkFavorito.isChecked = isPeliculaFavorita()
            }

        }
        return root
    }


    private suspend fun getPelicula(id: Int): MovieResponse {
        return withContext(Dispatchers.IO) {
//            val response = RetrofitInstance.moviesDbApi.getMovie(
//                id,
//                "6bc4475805ebbc4296bcfa515aa8df08",
//                "es-ES"
//            )
//            val response = peliculasApp.appModule.moviesApi.getMovie(
//                id,
//                "6bc4475805ebbc4296bcfa515aa8df08",
//                "es-ES"
//            )
            // Directamente con el repo
            val response = peliculasRepository.getMovie(id)
            // Con el viewmodel
//            val response = viewModel.getMovie(id,)
            response.body()!!

        }
    }

    private suspend fun isPeliculaFavorita(): Boolean {
        return withContext(Dispatchers.IO) {
//            peliculasDb.peliculasDao.findById(pelicula.id) != null
            peliculasRepository.getFavoritas().any {
                it.id == pelicula.id
            }
        }
    }

    private fun cambiarFavorita(esFavorita: Boolean) {
        val peliculaEntity = PeliculaEntity(
            id = pelicula.id,
            titulo = pelicula.title,
            fechaEstreno = pelicula.releaseDate,
            argumento = pelicula.overview,
            urlCaratula = pelicula.backdropPath,
            urlFondo = pelicula.posterPath
        )

        lifecycleScope.launch(Dispatchers.IO) {
            if (esFavorita) {
//                    peliculasDb.peliculasDao.add(peliculaEntity)
                peliculasRepository.insertFavorita(peliculaEntity)
            } else {
//                    peliculasDb.peliculasDao.delete(k)
                peliculasRepository.deleteFavorita(peliculaEntity)
            }
        }
    }
}