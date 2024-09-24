package com.example.constraintlayout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var checkbox : CheckBox
    private lateinit var buttonEnviar : Button

    companion object
    {
        const val CLAVE_NOMBRE : String = "nombre"
        const val CLAVE_EMAIL : String = "email"
        const val CLAVE_CHECKBOX : String = "checkboxVal"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        iniciarElementos()
    }

    private fun iniciarElementos()
    {
        editTextName = findViewById(R.id.editTextName)
        editTextEmail = findViewById(R.id.editTextEmail)
        checkbox = findViewById(R.id.checkBox)
        buttonEnviar = findViewById(R.id.buttonEnviar)

        buttonEnviar.setOnClickListener(){ _ ->
            //Toast.makeText(this, "Botón pulsado", Toast.LENGTH_SHORT).show()
            val nombre = editTextName.text.toString()
            val email = editTextEmail.text.toString()

            /*
            editTextName.setText("")
            editTextEmail.setText("")
            if(checkbox.isChecked)
                checkbox.isChecked = false
            else
                checkbox.isChecked = true
            */

            if(nombre.length > 0 && email.length > 0) {
                val intent = Intent(applicationContext, DetallesActivity::class.java)
                intent.putExtra(CLAVE_EMAIL, email)
                intent.putExtra(CLAVE_NOMBRE, nombre)
                intent.putExtra(CLAVE_CHECKBOX, checkbox.isChecked.toString())

                startActivity(intent)
            }
        }
    }
}