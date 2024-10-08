package com.example.navegacion_p4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

class HistoricoFragment : Fragment() {

    private lateinit var btnHistorico : Button
    private lateinit var editTextHistorico : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_historico, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnHistorico = requireView().findViewById(R.id.btnHistorico)
        editTextHistorico = requireView().findViewById(R.id.editTextHistorico)
        btnHistorico.setOnClickListener() { _ ->
            val destino = HistoricoFragmentDirections.actionHistoricoFragmentToDetallesFragment("a")
            findNavController().navigate(destino)
        }
    }
}