package com.example.constraintlayout

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.constraintlayout.MainActivity.Companion.CLAVE_CHECKBOX
import com.example.constraintlayout.MainActivity.Companion.CLAVE_EMAIL
import com.example.constraintlayout.MainActivity.Companion.CLAVE_NOMBRE

class DetallesActivity : AppCompatActivity() {

    private lateinit var buttonCancelled : Button
    private lateinit var buttonOk : Button
    private lateinit var textViewName : TextView
    private lateinit var textViewEmail : TextView
    private lateinit var textViewCheckbox : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalles)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textViewName = findViewById(R.id.textViewName)
        textViewEmail = findViewById(R.id.textViewEmail)
        textViewCheckbox = findViewById(R.id.textViewCheckbox)
        buttonCancelled = findViewById(R.id.buttonCancelled)
        buttonOk = findViewById(R.id.buttonOk)

        textViewName.setText(intent.getStringExtra(CLAVE_NOMBRE))
        textViewEmail.setText(intent.getStringExtra(CLAVE_EMAIL))
        textViewCheckbox.setText(intent.getStringExtra(CLAVE_CHECKBOX))


        //launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ resultado ->
        //    procesarResultado(resultado)
        //}

        buttonOk.setOnClickListener(){ _ ->
            intent.putExtra("resultado", "PATATISIMAMENTE ACEPTADO")
            setResult(RESULT_OK, intent)
            finish()
        }
        buttonCancelled.setOnClickListener(){ _ ->
            setResult(RESULT_CANCELED, intent)
            finish()
        }
    }
}