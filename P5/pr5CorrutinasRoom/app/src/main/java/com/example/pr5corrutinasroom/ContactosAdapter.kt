package com.example.pr5corrutinasroom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil3.load

class ContactosAdapter(

    private val listaContactos: List<Contacto>,
    //Esta función se guarda como atributo (recibe un contacto y no devuelve nada)
    private val onClickListener: (Contacto?) -> Unit

) : RecyclerView.Adapter<ContactosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val layoutElemento = R.layout.recycler_contacto_item
        val view = LayoutInflater.from(viewGroup.context).inflate(layoutElemento, viewGroup, false)
        return ViewHolder(view, onClickListener)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(listaContactos[position])
    }

    override fun getItemCount() = listaContactos.size


    class ViewHolder(
        view: View,
        onClickListener: (Contacto?) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        private val tvNombre: TextView = view.findViewById(R.id.tvNombre)
        private val tvTelefono:TextView = view.findViewById(R.id.tvTelefono)
        private val imageView: ImageView = view.findViewById(R.id.ivContactoPrevia)
        private var contactoActual: Contacto? = null

        init {
            view.setOnClickListener { it ->
                onClickListener(contactoActual)
            }
        }
        fun bind(contacto : Contacto) {
            contactoActual = contacto
            tvNombre.text = contacto.nombre
            tvTelefono.text = contacto.telefono
            imageView.load(contacto.imagenURL)

        }
    }



}
