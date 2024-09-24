package com.example.holamundoapp3

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var buttonEnviarNombre : Button
    private lateinit var textViewNombre : TextView
    private lateinit var editTextNombre : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        iniciarElementos() //Llamamos a la inicialización de elementos
        val myList = listOf("apple","mango","bread","Milks")
        val rand = (0..myList.size).random()
        Log.d("random", myList.get(rand))
        Log.d("miapp", "prueba")

        // Empieza el juego

        textViewNombre.setText("_ _ _ _ _")
        jugar(myList.get(rand))
    }

    private fun jugar(palabra: String)
    {
        buttonEnviarNombre.setOnClickListener(){ _ ->
            val text = editTextNombre.text
            if(text.length != 5)
            {
                Toast.makeText(this, "¡Introduce una palabra de 5 letras!", Toast.LENGTH_SHORT).show()
            }else
            {
                var newText = ""
                var i = 0
                for (letra in palabra) {
                    if (letra.equals(text.get(i)))
                        newText += letra
                    else
                        newText += "_"
                    i++;
                }
                if(text.equals(palabra)) {
                    textViewNombre.setText("La palabra era: " + palabra)
                }else {
                    textViewNombre.setText(newText)
                }
            }
        }
    }

    private fun iniciarElementos()
    {
        buttonEnviarNombre = findViewById(R.id.buttonEnviarNombre)
        textViewNombre = findViewById(R.id.textViewNombre)
        editTextNombre = findViewById(R.id.editTextNombre)

        //saludar()
    }

    /*
    private fun saludar()
    {
        buttonEnviarNombre.setOnClickListener(){ _ ->
            //Toast.makeText(this, "Botón pulsado", Toast.LENGTH_SHORT).show()
            val text = editTextNombre.text
            if(text.length < 2)
            {
                Toast.makeText(this, "El texto es demasiado pequeño", Toast.LENGTH_SHORT).show()
            }else {
                textViewNombre.setText(text)
                editTextNombre.setText("")
            }
        }
    }
     */
}