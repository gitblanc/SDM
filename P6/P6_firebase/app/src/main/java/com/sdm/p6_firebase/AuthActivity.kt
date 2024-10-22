package com.sdm.p6_firebase

import android.content.Intent
import android.os.Bundle
import android.provider.Telephony.Mms.Intents
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class AuthActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signupButton: Button
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_auth)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        checkLoggedUser()

        setup()
    }

    // Para la persistencia del usuario
    private fun checkLoggedUser()
    {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if(currentUser != null)
        {
            showHome(currentUser.email.toString(), ProviderType.BASIC)
        }
    }

    private fun setup()
    {
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        signupButton = findViewById(R.id.syncAppButton)
        loginButton = findViewById(R.id.loginButton)

        signupButton.setOnClickListener {
            if(emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()){
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(emailEditText.text.toString(), passwordEditText.text.toString())
                    .addOnCompleteListener {
                        // it es el nombre general para el parámetro que devuelve la función lambda
                        if (it.isSuccessful)
                        {
                            showHome(emailEditText.text.toString(), ProviderType.BASIC)
                        }else
                        {
                            showAlert()
                        }
                    }
            }
        }

        loginButton.setOnClickListener {
            if(emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()){
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(emailEditText.text.toString(), passwordEditText.text.toString())
                    .addOnCompleteListener {
                        // it es el nombre general para el parámetro que devuelve la función lambda
                        if (it.isSuccessful)
                        {
                            showHome(emailEditText.text.toString(), ProviderType.BASIC)
                        }else
                        {
                            showAlert()
                        }
                    }
            }
        }
    }

    // Crea un diálogo de error
    private fun showAlert() {
        val builder= AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }

    private fun showHome(email: String, provider: ProviderType)
    {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("email", email)
        intent.putExtra("provider", provider.name)
        startActivity(intent)
    }
}