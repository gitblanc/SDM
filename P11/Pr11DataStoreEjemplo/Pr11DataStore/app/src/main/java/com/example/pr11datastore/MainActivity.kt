package com.example.pr11datastore

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.pr11datastore.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var preferencias: PreferenciasDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        preferencias = PreferenciasDataStore(this)

        viewModel = ViewModelProvider(
            this, MainActivityViewModelProviderFactory(
                preferencias
            )
        ).get(MainActivityViewModel::class.java)


        findViewById<Button>(R.id.bGuardar).setOnClickListener {
            val color = findViewById<EditText>(R.id.etColor).text.toString()
            val edad = findViewById<EditText>(R.id.etEdad).text.toString().toIntOrNull() ?: 0
//            lifecycleScope.launch {
//                preferencias.guardarColor(color)
//                preferencias.guardarEdad(edad)
//            }
//            viewModel.guardarColor(color) // Llamar al ViewModel
//            viewModel.guardarEdad(edad) // Llamar al ViewModel
            viewModel.guardarPreferencias(color, edad)
        }

//        viewModel.preferenciasDataClass.color.observe(this) { color ->
//            Log.d("PR11", color)
//            findViewById<TextView>(R.id.tvColor).text = "Color: $color"
//        }
//
//        viewModel.preferenciasDataClass.edad.observe(this) { edad ->
//            Log.d("PR11", edad.toString())
//            findViewById<TextView>(R.id.tvEdad).text = "Edad: $edad"
//        }

        viewModel.preferenciasDataClass.


//        lifecycleScope.launch {
//            preferencias.color.collect { color ->
//                Log.d("PR11", color)
//                findViewById<TextView>(R.id.tvColor).text = "Color: $color".toString()
//            }
//        }
//
//        lifecycleScope.launch {
//            preferencias.edad.collect { edad ->
//                Log.d("PR11", edad.toString())
//                findViewById<TextView>(R.id.tvEdad).text = "Edad: $edad"
//            }
//        }
    }

}