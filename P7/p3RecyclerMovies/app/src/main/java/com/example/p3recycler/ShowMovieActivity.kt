package com.example.p3recycler

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.p3recycler.databinding.ActivityShowMovieBinding


class ShowMovieActivity : AppCompatActivity() {

private lateinit var binding: ActivityShowMovieBinding

    companion object {
        const val CLAVE_OBJ = "pelicula"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShowMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val pelicula = intent.getParcelableExtra(CLAVE_OBJ, Pelicula::class.java)
        if (pelicula != null)
            showMovie(pelicula)

        binding.toolbarLayout.title = title
        binding.toolbar.title = title
        binding.fab.setOnClickListener { view ->
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(pelicula?.urlTrailer)
                )
            )
        }
    }

    private fun showMovie(pelicula: Pelicula){
        binding.content.categoria?.text = pelicula.categoria
        binding.content.duracion?.text = "${pelicula.duracion/60}h"
        binding.content.argumento?.text = pelicula.argumento
        binding.content.estreno?.text = pelicula.fecha
        binding.content.caratula?.load(pelicula.urlCaratula)
        binding.imagenFondo.load(pelicula.urlFondo)
    }
}