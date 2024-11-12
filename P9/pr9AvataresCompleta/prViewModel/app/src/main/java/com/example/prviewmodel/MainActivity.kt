package com.example.prviewmodel

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil3.load
import com.example.prviewmodel.databinding.ActivityMainBinding
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    val estilos = arrayOf("avataaars","bottts","adventurer", "lorelei")

    private lateinit var viewModel : MainActivityViewModel


    override fun onResume() {
        super.onResume()
       (binding.cbEstilo.editText as MaterialAutoCompleteTextView).setSimpleItems(estilos)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this,
            MainActivityViewModelProviderFactory(estilos)).get(MainActivityViewModel::class.java)

        binding.bGenerarAvatar.setOnClickListener {
            val identificador = binding.tfCadena.editText!!.text.toString()
            val estilo = binding.cbEstilo.editText!!.text.toString()
            viewModel.generarAvatar(identificador,estilo)
        }

        binding.ckbFavorito.setOnClickListener { v ->
            viewModel.cambiarFavorito(binding.ckbFavorito.isChecked)

        }
        viewModel.avatarUIState.observe(this) { avatarUIState ->
            binding.tfCadena.editText!!.setText(avatarUIState.avatar.identificador)

            (binding.cbEstilo.editText as MaterialAutoCompleteTextView).setSimpleItems(estilos)
            if (estilos.indexOf(avatarUIState.avatar.estilo) != -1)
               (binding.cbEstilo.editText as MaterialAutoCompleteTextView).setText(avatarUIState.avatar.estilo, false)
            else
                (binding.cbEstilo.editText as MaterialAutoCompleteTextView).setText("Selecciona...", false)

            if (avatarUIState.imgURL.isNotEmpty())
                binding.ivAvatar.load(avatarUIState.imgURL)
            else
                binding.ivAvatar.setImageResource(R.drawable.baseline_person_24)

            binding.ckbFavorito.isChecked = avatarUIState.esFavorito
        }






    }

}