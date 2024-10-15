package com.example.pr5corrutinasroom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil3.load


class ContactoEditarFragment : Fragment() {
    private val args : ContactoEditarFragmentArgs by navArgs()
    private val contacto = args.contacto
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacto_editar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cargarValores()

        val bRecargarImagen = view.findViewById<ImageButton>(R.id.ibRecargarImagen)
        bRecargarImagen.setOnClickListener {
            val ivContactoPrevia = view.findViewById<ImageView>(R.id.ivContactoPrevia)
            val etImagenUrl = view.findViewById<EditText>(R.id.etImagenUrl)
            ivContactoPrevia.load(etImagenUrl.text.toString())
        }

        //Botón editar
        val bEditarContacto = view.findViewById<Button>(R.id.bEditarContacto)
        bEditarContacto.setOnClickListener {

        }


        //Botón cancelar
        val bCancelar = view.findViewById<Button>(R.id.bVolver)
        bCancelar.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun cargarValores() {
        val ivContactoPrevia = requireView().findViewById<ImageView>(R.id.ivContactoPrevia)
        ivContactoPrevia.load(contacto.imagenURL)
        val etNombre = requireView().findViewById<EditText>(R.id.etNombre)
        etNombre.setText(contacto.nombre)

        val etApellido = requireView().findViewById<EditText>(R.id.etApellido)
        etApellido.setText(contacto.apellido)

        val etImagenUrl = requireView().findViewById<EditText>(R.id.etImagenUrl)
        etImagenUrl.setText(contacto.imagenURL)

    }
}