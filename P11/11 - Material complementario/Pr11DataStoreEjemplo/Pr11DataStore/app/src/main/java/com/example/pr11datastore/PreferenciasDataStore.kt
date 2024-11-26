package com.example.pr11datastore


import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "prefs_usuario")

class PreferenciasDataStore(private val context: Context) {

    // claves para las preferencias
    companion object {
        val CLAVE_COLOR = stringPreferencesKey("color")
        val CLAVE_EDAD = intPreferencesKey("edad")
    }

    // Obtener el color favorito
    val color: Flow<String> = context.dataStore.data
        .map { prefs -> prefs[CLAVE_COLOR] ?: "Sin especificar" }

    // Obtener la edad
    val edad: Flow<Int> = context.dataStore.data
        .map { prefs -> prefs[CLAVE_EDAD] ?: 0 }

    // Guardar el color favorito
    suspend fun guardarColor(color: String) {
        context.dataStore.edit { prefs ->
            prefs[CLAVE_COLOR] = color
        }
    }

    // Guardar la edad
    suspend fun guardarEdad(edad: Int) {
        context.dataStore.edit { prefs ->
            prefs[CLAVE_EDAD] = edad
        }
    }
}