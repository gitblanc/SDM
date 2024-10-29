package com.example.p3recycler

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.p3recycler.DetallesPeliculaActivity.Companion.CLAVE_OBJ
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
    private var listaPeliculas : MutableList<Pelicula> = mutableListOf()
    private lateinit var recyclerView : RecyclerView

    private lateinit var launcherNueva : ActivityResultLauncher<Intent>
    private lateinit var fabNueva : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        listaPeliculas= leerPeliculasDesdeAssets(this,"lista_peliculas_url_utf8.csv")
        generarRecyclerContactos()


        //Botón FAB para crear contacto
        fabNueva = findViewById(R.id.fabNuevo)
        fabNueva.setOnClickListener() {
            launcherNueva.launch(Intent(applicationContext,NuevaPeliculaActivity::class.java))
        }

        launcherNueva = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultado ->
            insertarContacto(resultado)
        }


    }

    private fun generarRecyclerContactos() {
        val customAdapter = PeliculasAdapter(listaPeliculas) { pelicula ->
            //val intent = Intent(this, DetallesPeliculaActivity::class.java)
            val intent = Intent(this, ShowMovieActivity::class.java)
            intent.putExtra(CLAVE_OBJ, pelicula)
            startActivity(intent)
        }
        recyclerView = findViewById(R.id.recyclerPeliculas)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = customAdapter
    }


    private fun insertarContacto(resultado: ActivityResult) {
        if (resultado.resultCode == RESULT_OK)
        {
            val contacto = resultado.data!!.getParcelableExtra("contacto", Pelicula::class.java)
            if (contacto != null)
            {
                listaPeliculas.add(contacto)
                //Ten en cuenta que notifiyDataSetChanged nunca es la mejor opción.
                recyclerView.adapter!!.notifyDataSetChanged()
            }
        }
    }

    private fun leerPeliculasDesdeAssets(context: Context, nombreArchivo: String): MutableList<Pelicula> {
        val peliculas = mutableListOf<Pelicula>()

        // Usar el AssetManager para abrir el archivo
        val inputStream = context.assets.open(nombreArchivo)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))

        // Leer las líneas del archivo y procesarlas
        bufferedReader.useLines { lines ->
            lines.forEach { line ->
                val campos = line.split(";")
                if (campos.size == 9) {
                    val pelicula = Pelicula(
                        id = campos[0].toIntOrNull() ?: 0,
                        titulo = campos[1],
                        argumento = campos[2],
                        categoria = campos[3],
                        duracion = campos[4].toIntOrNull() ?: 0,
                        fecha = campos[5],
                        urlCaratula = campos[6],
                        urlFondo = campos[7],
                        urlTrailer = campos[8]
                    )
                    peliculas.add(pelicula)
                }
            }
        }

        return peliculas
    }


}