package com.example.pr5corrutinasroom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil3.load


class ContactoDetallesFragment : Fragment() {
    private lateinit var tvNombreApellidos : TextView
    private lateinit var tvTelefono : TextView
    private lateinit var ivImagenContacto: ImageView

    private val args : ContactoDetallesFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contacto_detalles, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tvNombreApellidos = view.findViewById(R.id.tvNombreApellidos)
        tvTelefono = view.findViewById(R.id.tvTelefono)
        ivImagenContacto = view.findViewById(R.id.ivContactoPrevia)
        val contacto = args.contacto

        val infoContacto = "${contacto.nombre} ${contacto.apellido}"
        tvNombreApellidos.text = infoContacto

        tvTelefono.text = contacto.telefono
        ivImagenContacto.load(contacto.imagenURL)

        //Listeners
        val bCancelar = view.findViewById<Button>(R.id.bVolver)
        bCancelar.setOnClickListener { findNavController().popBackStack() }

        //Navegar a Editar
        val bEditarContacto = view.findViewById<Button>(R.id.bEditarContacto)
        bEditarContacto.setOnClickListener {
            val destino = ContactoDetallesFragmentDirections
                .actionContactoDetallesFragmentToContactoEditarFragment(contacto)
            findNavController().navigate(destino)

        }

        //Eliminar
        val bEliminarContacto = view.findViewById<Button>(R.id.bEliminarContacto)
        bEliminarContacto.setOnClickListener {

        }

    }
}