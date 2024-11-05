package com.example.p3recycler

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class NuevaPeliculaActivity : AppCompatActivity() {

    private lateinit var botonCrear: Button
    private lateinit var botonCancelar: Button
    private lateinit var etNombre: EditText
    private lateinit var etTelefono: EditText
    private lateinit var etId: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_nueva_pelicula)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etNombre = findViewById(R.id.etNombre)
        etTelefono = findViewById(R.id.etTelefono)
        etId = findViewById(R.id.etId)

        botonCrear = findViewById(R.id.bCrear)
        botonCrear.setOnClickListener {
            val contacto = validaDatosContacto()

            if (contacto != null) {
                val intentResultado = Intent()
                intentResultado.putExtra("contacto", contacto)
                setResult(RESULT_OK, intentResultado)
                finish()
            }

        }

        botonCancelar = findViewById(R.id.bCancelar)
        botonCancelar.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    private fun validaDatosContacto(): Pelicula? {
        try {
            val id = etId.text.toString().toInt()
            val pelicula = Pelicula(
                id,
                etNombre.text.toString(),
                etTelefono.text.toString(),
                "",
                100,
                "",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/9/93/Google_Contacts_icon.svg/240px-Google_Contacts_icon.svg.png",
                "",
                ""
            )
            return pelicula
        } catch (e: Exception) {
            return null
        }

    }


}