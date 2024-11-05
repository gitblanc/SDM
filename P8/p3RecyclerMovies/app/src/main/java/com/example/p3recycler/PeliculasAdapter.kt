package com.example.p3recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load

class PeliculasAdapter(

    private val listaPeliculas: List<Pelicula>,
    //Esta función se guarda como atributo (recibe una pelicula y no devuelve nada)
    private val onClickListener: (Pelicula?) -> Unit

) : RecyclerView.Adapter<PeliculasAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val layoutElemento = R.layout.pelicula_item
        val view = LayoutInflater.from(viewGroup.context).inflate(layoutElemento, viewGroup, false)
        return ViewHolder(view, onClickListener)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(listaPeliculas[position])
    }

    override fun getItemCount() = listaPeliculas.size


    class ViewHolder(
        view: View,
        onClickListener: (Pelicula?) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        val tvTitulo: TextView = view.findViewById(R.id.tvTitulo)
        val imageView: ImageView = view.findViewById(R.id.ivCaratula)
        val tvFechaEstreno: TextView = view.findViewById(R.id.tvFechaEstreno)
        var peliculaActual: Pelicula? = null

        init {
            view.setOnClickListener { it ->
                onClickListener(peliculaActual)
            }
        }
        fun bind(pelicula : Pelicula) {
            peliculaActual = pelicula
            tvTitulo.text = pelicula.titulo
            imageView.load(pelicula.urlCaratula)
            tvFechaEstreno.text = pelicula.fecha
        }
    }



}

