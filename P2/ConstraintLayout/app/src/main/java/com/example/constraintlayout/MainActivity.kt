package com.example.constraintlayout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var checkbox : CheckBox
    private lateinit var buttonEnviar : Button
    private lateinit var textViewAceptado : TextView

    companion object
    {
        const val CLAVE_NOMBRE : String = "nombre"
        const val CLAVE_EMAIL : String = "email"
        const val CLAVE_CHECKBOX : String = "checkboxVal"
    }

    private lateinit var launcher : ActivityResultLauncher<Intent>

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
        textViewAceptado = findViewById(R.id.textViewAceptado)

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

                launcher.launch(intent)
                //startActivity(intent)
            }
        }

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ resultado ->
            procesarResultado(resultado)
        }
    }

    private fun procesarResultado(resultado: ActivityResult) {
        if(resultado.resultCode == RESULT_OK) {
            Toast.makeText(this, "¡Se aceptó!", Toast.LENGTH_SHORT).show()
            val text = resultado.data?.getStringExtra("resultado")
            textViewAceptado.setText(text)
        }
        else
            Toast.makeText(this, "¡Se canceló!", Toast.LENGTH_SHORT).show()
    }
}