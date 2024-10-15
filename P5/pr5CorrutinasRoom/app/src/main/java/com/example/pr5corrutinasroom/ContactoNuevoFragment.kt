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
import coil3.load


class ContactoNuevoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contacto_nuevo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val bRecargarImagen = view.findViewById<ImageButton>(R.id.ibRecargarImagen)
        bRecargarImagen.setOnClickListener {
            val ivContactoPrevia = view.findViewById<ImageView>(R.id.ivContactoPrevia)
            val etImagenUrl = view.findViewById<EditText>(R.id.etImagenUrl)
            ivContactoPrevia.load(etImagenUrl.text.toString())
        }

        //Botón crear
        val bCrearContacto = view.findViewById<Button>(R.id.bCrearContacto)
        bCrearContacto.setOnClickListener {

        }


        //Botón cancelar
        val bCancelar = view.findViewById<Button>(R.id.bVolver)
        bCancelar.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}