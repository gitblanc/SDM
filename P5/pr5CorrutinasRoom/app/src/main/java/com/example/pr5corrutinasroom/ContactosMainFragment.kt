package com.example.pr5corrutinasroom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.BufferedReader
import java.io.InputStreamReader


class ContactosMainFragment : Fragment() {
    private lateinit var recyclerContactos : RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contactos_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inicializaRecyclerContactos()


        val fabNuevoContacto = view.findViewById<FloatingActionButton>(R.id.fabNuevoContacto)
        fabNuevoContacto.setOnClickListener {
            val destino = ContactosMainFragmentDirections
                .actionContactosMainFragmentToContactoNuevoFragment()
            findNavController().navigate(destino)
        }
    }

    private fun inicializaRecyclerContactos() {
        recyclerContactos = requireView().findViewById(R.id.recyclerContactos)
        //Fíjate cómo le pasamos el contexto. Ya no es "this" ¡Estamos en un fragment!
        recyclerContactos.layoutManager = LinearLayoutManager(requireContext())

        var listaContactos = cargarContactos()

        recyclerContactos.adapter = ContactosAdapter(listaContactos) { contacto ->
            val destino = ContactosMainFragmentDirections
                .actionContactosMainFragmentToContactoDetalles(contacto!!)
            findNavController().navigate(destino)

        }
    }

    private fun cargarContactos(): List<Contacto> {
        //Fíjate en el requireActivity... ¡Estamos en un fragment!
        val minput = InputStreamReader(requireActivity().assets.open("contactos.csv"))
        val reader = BufferedReader(minput)
        return reader.lineSequence()
            .map { linea ->   linea.split(';', limit = 0) }
            .filter { array -> array.size == 4 }
            .map { array -> Contacto(
                array[0],
                array[1],
                array[2],
                array[3])}
            .toList()

    }

}