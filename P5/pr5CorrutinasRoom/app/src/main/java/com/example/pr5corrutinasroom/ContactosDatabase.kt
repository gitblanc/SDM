package com.example.pr5corrutinasroom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


//Recuerda que esta clase está incompleta

//@Database(entities = [Aquí el nombre de las clases con @Entity separadas por comas. Ejemplo: Nota::class, Usuario::class], version = 1, exportSchema = false)
@Database(entities = [Contacto::class], version = 1, exportSchema = false)
abstract class ContactosDatabase : RoomDatabase() {

    //Una referencia abstracta (val) a cada dao implementado.
    abstract val contactosDao : ContactoDao

    //Singleton
    companion object {
        @Volatile
        private var SINGLETON: ContactosDatabase? = null

        fun getDB(context: Context?): ContactosDatabase {
            if (SINGLETON != null)
                return SINGLETON as ContactosDatabase


            val instancia = Room.databaseBuilder(context!!.applicationContext, ContactosDatabase::class.java, "contactos.db")
                //El código de debajo, establecería la BD proporcionada como la inicial
                //NOTA: No la recrea constantemente entre ejecuciones. Tendrás que desinstalar la aplicación en el móvil.
                //.createFromAsset("contactos.db")
                .build()
            SINGLETON = instancia
            return SINGLETON as ContactosDatabase
        }
    }
}