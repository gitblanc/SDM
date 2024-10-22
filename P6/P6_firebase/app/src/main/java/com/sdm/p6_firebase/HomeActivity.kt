package com.sdm.p6_firebase

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

enum class ProviderType {
    BASIC
}

class HomeActivity : AppCompatActivity() {

    private lateinit var emailTextView: TextView
    private lateinit var providertextView: TextView
    private lateinit var logoutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setup()
    }

    private fun setup()
    {
        emailTextView = findViewById(R.id.emailTextView)
        providertextView = findViewById(R.id.providerTextView)
        logoutButton = findViewById(R.id.logoutButton)

        obtenerDatos(intent)

        logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut() // para el logout
            finish()
        }
    }

    private fun obtenerDatos(intent: Intent)
    {
        val email = intent.getStringExtra("email")
        val provider = intent.getStringExtra("provider")

        emailTextView.text = email
        providertextView.text = provider
    }
}