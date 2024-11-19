package com.example.pr10integracion.presentacion

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.pr10integracion.R
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
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val bottomViewNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomViewNav.setupWithNavController(navHostFragment.navController)

        bottomViewNav.setOnItemSelectedListener { item ->
            NavigationUI.onNavDestinationSelected(item, navHostFragment.navController)
            navHostFragment.navController.popBackStack(destinationId = item.itemId, inclusive = false)
            true
        }

        bottomViewNav.setOnItemReselectedListener { item ->
            navHostFragment.navController.popBackStack(destinationId = item.itemId, inclusive = false)
        }

    }


}