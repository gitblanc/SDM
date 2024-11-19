package com.example.pr10integracion.presentacion.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import com.example.pr10integracion.R
import com.example.pr10integracion.datos.db.entities.PeliculaEntity

class PeliculaEntityAdapter(

    private val _listaPeliculas: List<PeliculaEntity>,
    private val onClickListener: (Int) -> Unit

) : RecyclerView.Adapter<PeliculaEntityAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val layoutElemento = R.layout.pelicula_item
        val view = LayoutInflater.from(viewGroup.context).inflate(layoutElemento, viewGroup, false)
        return ViewHolder(view, onClickListener)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(_listaPeliculas[position])
    }

    override fun getItemCount() = _listaPeliculas.size


    class ViewHolder(
        view: View,
        onClickListener: (Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        private val tvTitulo: TextView = view.findViewById(R.id.tvTitulo)
        private val imageView: ImageView = view.findViewById(R.id.ivPortada)
        private var _peliculaActual: PeliculaEntity? = null

        init {
            view.setOnClickListener { it ->
                onClickListener(_peliculaActual!!.id)
            }
        }
        fun bind(pelicula : PeliculaEntity) {
            _peliculaActual = pelicula
            tvTitulo.text = pelicula.titulo
            imageView.load("https://image.tmdb.org/t/p/original/" + pelicula.urlCaratula) {
                crossfade(true)
                crossfade(150)
            }


        }
    }

}