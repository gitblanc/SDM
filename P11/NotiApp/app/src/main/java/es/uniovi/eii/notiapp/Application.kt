package es.uniovi.eii.notiapp

import android.os.Build
import androidx.annotation.RequiresApi

class Application : android.app.Application() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val name = "Canal simple"
        val descriptionText = "Canal simple de notificaciones"
        val importance = android.app.NotificationManager.IMPORTANCE_DEFAULT
        val channel = android.app.NotificationChannel(MainActivity.CANAL_SIMPLE, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: android.app.NotificationManager = getSystemService(android.content.Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}