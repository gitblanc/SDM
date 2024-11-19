package com.example.pr10integracion.presentacion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pr10integracion.PeliculasApp
import com.example.pr10integracion.R
import com.example.pr10integracion.datos.PeliculasRepository
import com.example.pr10integracion.datos.db.PeliculasDb
import com.example.pr10integracion.datos.db.entities.PeliculaEntity
import com.example.pr10integracion.datos.modelo.MovieResponse
//import com.example.pr10integracion.datos.network.RetrofitInstance
import com.example.pr10integracion.presentacion.adapters.PeliculaEntityAdapter
import com.example.pr10integracion.presentacion.adapters.PeliculasAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FavoritasPeliculasFragment : Fragment() {
    private lateinit var recyclerViewMovies: RecyclerView
    private lateinit var peliculasAdapter: PeliculaEntityAdapter

    //    private lateinit var peliculasDb: PeliculasDb
//    private val peliculasDb by lazy { PeliculasDb.getDB(context) }
    private val peliculasApp = PeliculasApp // val porque ya esta inicializado
    private val peliculasRepository = PeliculasApp.appModule.peliculasRepository
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_favoritas_peliculas, container, false)

//        peliculasDb = PeliculasDb.getDB(context)
        recyclerViewMovies = root.findViewById(R.id.recycler_favoritas_peliculas)
        recyclerViewMovies.layoutManager = LinearLayoutManager(context)

        //Carga la lista de películas favoritas.
        lifecycleScope.launch {
            val listaPeliculas = cargarPeliculas()
            withContext(Dispatchers.Main) {
                peliculasAdapter = PeliculaEntityAdapter(listaPeliculas) { id ->
                    val destino = FavoritasPeliculasFragmentDirections
                        .actionFavoritasPeliculasFragmentToDetallesPeliculaFragment(id)
                    findNavController().navigate(destino)
                }
                recyclerViewMovies.adapter = peliculasAdapter
            }
        }
        return root
    }


    private suspend fun cargarPeliculas(): List<PeliculaEntity> {
        return withContext(Dispatchers.IO) {
//            peliculasDb.peliculasDao.getAll()
            peliculasRepository.getFavoritas()
        }
    }

}