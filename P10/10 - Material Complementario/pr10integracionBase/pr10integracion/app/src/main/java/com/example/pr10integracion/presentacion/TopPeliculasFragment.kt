package com.example.pr10integracion.presentacion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pr10integracion.PeliculasApp
import com.example.pr10integracion.R
//import com.example.pr10integracion.datos.network.RetrofitInstance
import com.example.pr10integracion.presentacion.adapters.PeliculasAdapter
import com.example.pr10integracion.presentacion.viewmodels.TopPeliculasViewModel
import com.example.pr10integracion.presentacion.viewmodels.TopPeliculasViewModelProviderFactory

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TopPeliculasFragment : Fragment() {

    private lateinit var recyclerViewMovies: RecyclerView
    private lateinit var peliculasAdapter: PeliculasAdapter
    private val peliculasApp = PeliculasApp // val porque ya esta inicializado
    private val peliculasRepository = peliculasApp.appModule.peliculasRepository

    private lateinit var viewModel : TopPeliculasViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_top_peliculas, container, false)
        recyclerViewMovies = root.findViewById(R.id.recycler_top_peliculas)
        recyclerViewMovies.layoutManager = LinearLayoutManager(context)

        viewModel =
            ViewModelProvider(this, TopPeliculasViewModelProviderFactory(peliculasRepository)).get(
                TopPeliculasViewModel::class.java
            )

        viewModel.favoritas.observe(viewLifecycleOwner){
            peliculasAdapter.updatePeliculas(it)
        }

        //Carga la lista de películas populares.
//        lifecycleScope.launch(Dispatchers.IO) {
//            val respuesta = RetrofitInstance.moviesDbApi.listMovies(
//                "popular",
//                "6bc4475805ebbc4296bcfa515aa8df08",
//                "es-ES",
//                1
//            )
//            val respuesta = peliculasApp.appModule.moviesApi.listMovies(
//                "popular",
//                "6bc4475805ebbc4296bcfa515aa8df08",
//                "es-ES",
//                1
//            )
            // Con un repositorio
//            val respuesta = peliculasRepository.getPopularMovies()


//            var listaPeliculas = emptyList<MovieResponse>()
//            if (respuesta.body() != null)
//                listaPeliculas = respuesta.body()!!.results
//
//            withContext(Dispatchers.Main) {
//                peliculasAdapter = PeliculasAdapter(listaPeliculas) { id ->
//                    val destino = TopPeliculasFragmentDirections
//                        .actionTopPeliculasFragmentToDetallesPeliculaFragment(id)
//                    findNavController().navigate(destino)
//                }
//                recyclerViewMovies.adapter = peliculasAdapter
//            }
//        }
        return root
    }


}