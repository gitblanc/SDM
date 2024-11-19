package com.example.pr10integracion.datos.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pr10integracion.datos.db.entities.PeliculaEntity

@Database(entities = [PeliculaEntity::class], version = 1, exportSchema = false)
abstract class PeliculasDb : RoomDatabase(){
    abstract  val peliculasDao : PeliculasDao

    //Singleton
    companion object {
        @Volatile
        private var SINGLETON: PeliculasDb? = null

        fun getDB(context: Context?): PeliculasDb {
            if (SINGLETON != null)
                return SINGLETON as PeliculasDb

            val instancia = Room.databaseBuilder(context!!.applicationContext, PeliculasDb::class.java, "peliculas.db")
                .build()
            SINGLETON = instancia
            return SINGLETON as PeliculasDb
        }
    }

}