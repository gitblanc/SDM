package es.uniovi.eii.notiapp

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat

/**
 * Un BroadCastReceiver o, en castellano, Receptor de emisiones.
 * Nos obliga a implementar el onReceive.
 * Funcionan en segundo plano ¿qué quiere decir esto?
 * Échale un ojo al Manifest.
 */
class ProgramadaNotification : BroadcastReceiver() {

    companion object {
        const val PROGRAMADA_NOTIFICATION_ID = 2
    }

    /*
        Cuando nos notifiquen, se activa este método.
     */
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("PR11", "Entro en el onReceive de ProgramadaNotification")
        crearNotificacionRepetible(context!!)
    }



    /*
     * Este código es muy similar al que ya tienes en el MainActivity.
     * Simplemente, pasamos otra ID.
     */
    private fun crearNotificacionRepetible(context: Context) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val notificacion = NotificationCompat.Builder(context,MainActivity.CANAL_SIMPLE)
            .setSmallIcon(R.drawable.baseline_local_pizza_24)
            .setContentTitle("Notificación programada")
            .setContentText("Esto es el cuerpo.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(PROGRAMADA_NOTIFICATION_ID,notificacion)
    }

}