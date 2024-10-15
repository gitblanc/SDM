package com.example.pr5corrutinasroom

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView




class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bottomViewNav = findViewById<BottomNavigationView>(R.id.bottomNav)

        // Otra forma con respecto a la vista en clase empleando <fragment> (era más clásica).
        // Esta es más directa, pero introduce el supportFragmentManager
        // Échale un ojo, para que veas usos más directos y habituales:
        //      https://developer.android.com/guide/fragments/fragmentmanager?hl=es-419


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        bottomViewNav.setupWithNavController(navHostFragment.navController)

    }


}