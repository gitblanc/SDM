package com.example.prviewmodel

import android.os.Bundle
import android.util.Log
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

    private lateinit var binding: ActivityMainBinding
    val estilos = arrayOf("avataaars", "bottts", "adventurer", "lorelei")

    private lateinit var viewModel: MainActivityViewModel


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

        viewModel = ViewModelProvider(
            this,
            MainActivityViewModelProviderFactory(estilos)
        ).get(MainActivityViewModel::class.java)

        binding.bGenerarAvatar.setOnClickListener {
            val identificador = binding.tfCadena.editText!!.text.toString()
            val estilo = binding.cbEstilo.editText!!.text.toString()
            viewModel.generarAvatar(identificador, estilo)
        }

        binding.ckbFavorito.setOnClickListener { v ->
            viewModel.cambiarFavorito(binding.ckbFavorito.isChecked)

        }
        viewModel.avatarUIState.observe(this) { avatarUIState ->
            binding.tfCadena.editText!!.setText(avatarUIState.avatar.identificador)

            (binding.cbEstilo.editText as MaterialAutoCompleteTextView).setSimpleItems(estilos)
            if (estilos.indexOf(avatarUIState.avatar.estilo) != -1)
                (binding.cbEstilo.editText as MaterialAutoCompleteTextView).setText(
                    avatarUIState.avatar.estilo,
                    false
                )
            else
                (binding.cbEstilo.editText as MaterialAutoCompleteTextView).setText(
                    "Selecciona...",
                    false
                )

            if (avatarUIState.imgURL.isNotEmpty())
                binding.ivAvatar.load(avatarUIState.imgURL)
            else
                binding.ivAvatar.setImageResource(R.drawable.baseline_person_24)

            binding.ckbFavorito.isChecked = avatarUIState.esFavorito
        }
    }
}

//private lateinit var binding: ActivityMainBinding
//
//private val estilos = arrayOf("avataaars", "bottts", "adventurer", "lorelei")
//private val listaFavs = mutableListOf<Avatar>()
//
//
//override fun onCreate(savedInstanceState: Bundle?) {
//    super.onCreate(savedInstanceState)
//    enableEdgeToEdge()
//
//    binding = ActivityMainBinding.inflate(layoutInflater)
//    val view = binding.root
//    setContentView(view)
//    ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
//        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//        insets
//    }
//    binding.ivAvatar.setImageResource(R.drawable.baseline_person_24)
//
//
//    //Listener al botón bGenerarAvatar.
//    binding.bGenerarAvatar.setOnClickListener() {
//        generarAvatar()
//        // Si no está en favoritos
//        if (!esFavorito()) {
//            binding.ckbFavorito.isChecked = false
//        } else {
//            binding.ckbFavorito.isChecked = true
//        }
//
//    }
//
//    //Listener al checkBox -> Insertará o eliminará de favoritos.
//    binding.ckbFavorito.setOnClickListener() {
//        if (binding.ckbFavorito.isChecked) {
//            insertarFav()
//        } else {
//            eliminarFav()
//        }
//    }
//
//}

//override fun onResume() {
//    super.onResume()
//    (binding.cbEstilo.editText as MaterialAutoCompleteTextView).setSimpleItems(estilos)
//}
//
//private fun obtenerAvatar(): Avatar {
//    //Devuelve un objeto avatar.
//    val cadena = binding.tfCadena.editText!!.text.toString()
//    val estilo = binding.cbEstilo.editText!!.text.toString()
//    return Avatar(cadena, estilo)
//}
//
//private fun generarAvatar() {
//    //NOTA: En una entrega debería haber validación de datos.
//
//    //Obtiene los valores insertados y carga la imagen.
//    val cadena = binding.tfCadena.editText!!.text.toString()
//    val estilo = binding.cbEstilo.editText!!.text.toString()
//    binding.ivAvatar.load("https://api.dicebear.com/9.x/$estilo/svg?seed=$cadena")
//}
//
//private fun eliminarFav() {
//    //Debe obtener el avatar y borrarlo de la lista de favoritos
//    obtenerAvatar().let {
//        listaFavs.remove(it)
//    }
//}
//
//private fun insertarFav() {
//    //Debe obtener el avatar y añadirlo a la lista de favoritos.
//    obtenerAvatar().let {
//        listaFavs.add(it)
//    }
//}
//
//private fun esFavorito(): Boolean {
//    //Debe obtener el avatar y devolverlo si está incluido o no en la lista de favoritos.
//    return listaFavs.contains(obtenerAvatar())
//}