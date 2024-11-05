package com.example.p3recycler

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.load

class DetallesPeliculaActivity : AppCompatActivity() {
    private lateinit var ivContacto : ImageView
    private lateinit var tvNombre : TextView
    private lateinit var tvTelefono: TextView
    private lateinit var bVolver: Button

    companion object {
        const val CLAVE_OBJ = "pelicula"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalles_pelicula)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        inicializarComponentes()

        //Recuperamos el contacto recibido en el intent
        val pelicula = intent.getParcelableExtra(CLAVE_OBJ, Pelicula::class.java)
        if (pelicula != null)
            cargarPelicula(pelicula)


    }

    private fun cargarPelicula(pelicula: Pelicula) {
        tvNombre.text = pelicula.titulo
        tvTelefono.text = pelicula.categoria
        ivContacto.load(pelicula.urlCaratula)
    }

    private fun inicializarComponentes() {
        ivContacto = findViewById(R.id.ivContacto)
        tvNombre = findViewById(R.id.tvNombre)
        tvTelefono = findViewById(R.id.tvTelefono)
        bVolver = findViewById(R.id.bVolver)
        bVolver.setOnClickListener{ finish() }
    }
}