package es.uniovi.eii.notiapp

import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var btnNotiLocal: Button
    private lateinit var btnNotiProgramada: Button

    private lateinit var btnNotiRepetible: Button
    private lateinit var btnCancelarRepetible: Button

    private lateinit var btnConcederPermisos: Button

    //De tipo String porque el código de los permisos viaja como String
    private lateinit var solicitarPermisosLauncher: ActivityResultLauncher<String>

    companion object {
        const val CANAL_SIMPLE = "Canal simple"
        const val NOTIFICACION_SIMPLE_ID = 1
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnNotiLocal = findViewById(R.id.btnLocal)
        btnNotiProgramada = findViewById(R.id.btnProgramada)
        btnNotiRepetible = findViewById(R.id.btnRepetible)
        btnCancelarRepetible = findViewById(R.id.btnCancelarRepetible)
        btnConcederPermisos = findViewById(R.id.btnConcederPermisos)

        //API 33+ conceder permisos explícitamente
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            solicitarPermisosLauncher =
                registerForActivityResult(ActivityResultContracts.RequestPermission()) { permitido ->
                    if (!permitido)
                        Toast.makeText(this, "No se mostrarán notificaciones", Toast.LENGTH_LONG)
                            .show()
                    else
                        btnConcederPermisos.isVisible = false
                }
            if (!tienePermisoPostApi33())
                btnConcederPermisos.isVisible = true

            btnConcederPermisos.setOnClickListener {
                solicitarPermisosNotificaciones()
            }
        }


        // ¡OJO!

        //EJEMPLO 1: Notificación local
        btnNotiLocal.setOnClickListener {
            crearNotificacionSimple()
        }

        //EJEMPLO 2: Notificación programada
        btnNotiProgramada.setOnClickListener {
            crearNotificacionProgramada()
        }

        //EJERCICIO : Notificación repetible
        btnNotiRepetible.setOnClickListener {
            crearNotificacionaRepetible()
        }

        //EEJERCICIO: Cancelar repetible.
        btnCancelarRepetible.setOnClickListener {
            cancelarNotificacionProgramada()
        }


    }

    /*
     * En API 33+ el usuario debe permitir explícitamente las notificaciones
     */
    private fun tienePermisoPostApi33(): Boolean {
        //Ojo -> añadir a mano el import android.Manifest
        //para que reconzoca el permission.POST_NOTIFICATIONS
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    }

    /*
     * API3++ Solicitar permiso
     */
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun solicitarPermisosNotificaciones() {
        //Si no está habilitado el permiso de enviar notificaciones.
        if (!tienePermisoPostApi33()) {
            //Solicitamos el permiso de notificaciones.
            solicitarPermisosLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)

        }
    }


    /*
     * Sabes (o deberías saber) lo que es un canal. Al menos, a nivel usuario:
     * https://developer.android.com/static/images/ui/notifications/channel-settings_2x.png
     */
//    fun crearCanalNotificacion() {
//        //Si API 26 o superior.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val idCanal = CANAL_SIMPLE
//            val nombreCanal = "El nombre del canal"
//            val importancia = NotificationManager.IMPORTANCE_DEFAULT
//            val canal = NotificationChannel(idCanal, nombreCanal, importancia)
//            canal.description = "Descripción del canal"
//            //Añadimos el canal al servicio de notificaciones.
//            val notificationManager =
//                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            //notificationManager.createNotificationChannel(canal)
//        }
//    }


    /**
     * Crea una notificación local simple.
     * Las notificaciones son muy configurables. Se crean mediante un builder.
     * Puedes añadir incluso botones de acción, barras de progreso...
     *      https://developer.android.com/develop/ui/views/notifications/build-notification#Actions
     */
    fun crearNotificacionSimple() {
        //Empezamos a construir la notificación
        //¡Fíjate que le pasamos el canal aquí!

        val notificacion = NotificationCompat.Builder(applicationContext, CANAL_SIMPLE)
            .setSmallIcon(R.drawable.baseline_local_pizza_24) //El icono. Es obligatorio. El resto es opcional.
            .setContentTitle("Notificación simple") // Título
            .setContentText("Esto es el cuerpo.") //Cuerpo
            .setPriority(NotificationCompat.PRIORITY_DEFAULT) //Compatibilidad con API 25 y anteriores. (Android 7.1)

        //El estilo/formato es configurable, échale un ojo:
        //      https://developer.android.com/develop/ui/views/notifications/expanded


        //Una vez conozcas un poco más el proyecto, prueba a descomentar el siguiente código.

        /*
                val intent = Intent(this, MainActivity::class.java).apply {
                    //Evitamos duplicidad
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

                notificacion.setContentIntent(pendingIntent)
                    .setAutoCancel(true) // La notificación se cierra al pulsarla.
        */


        //Accedemos al servicio de notificaciones
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        /*
            Fíjate que siempre pasamos la misma ID. En este caso, porque estoy asumiendo
            que siempre tendremos la MISMA notificación de este tipo.

         */


        manager.notify(NOTIFICACION_SIMPLE_ID, notificacion.build())
    }


    /**
     * Pásate primero por el ProgramadaNotification.tk
     */
    private fun crearNotificacionProgramada() {
        val intent = Intent(applicationContext, ProgramadaNotification::class.java)

        //Fíjate, que ahora el pendingIntent lo creamos de forma diferente.
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            ProgramadaNotification.PROGRAMADA_NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        //Temporizador, mediante el servicio de alarma.
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        //API 31+ y permiso para alarmas exactas o API < 31
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && alarmManager.canScheduleExactAlarms() || Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {

            //Alarma exacta (por eso la condición enorme anterior).
            //RTC_WAKEUP: Activa el dispositivo a fin de activar el intent pendiente a la hora especificada
            //El momento en el que debe suceder (de aquí a 5 segundos).
            //El PendingIntent.
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                Calendar.getInstance().timeInMillis + 5000,
                pendingIntent
            )
            Log.d("PR11", "Establecido un evento en 5 segundos")
        } else // 31 y sin permiso. Solicitamos
            startActivity(Intent(ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
    }


    /**
     * Para crear una notificación repetible, el código es similar. Simplemente no es "Exacta".
     * Haz que se repita cada 5 segundos. Luego vete mirando el Logcat ¿Qué ocurre?
     */
    private fun crearNotificacionaRepetible() {
        val intent = Intent(applicationContext, ProgramadaNotification::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            ProgramadaNotification.PROGRAMADA_NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            Calendar.getInstance().timeInMillis + 5000,
            5000,
            pendingIntent
        )
    }


    /**
     * Para cancelar una alarma repetible, debes hacer uso del método cancel de un objeto AlarmManager
     * Debe recibir un PendingItem ¿Sabemos el pending intent que queremos cancelar?
     */
    private fun cancelarNotificacionProgramada() {
        val intent = Intent(applicationContext, ProgramadaNotification::class.java)
        //Sabemos que se cancelará perfectamente porque solamente usamos una id. (PROGRAMADA_NOTIFICATION_ID)
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            ProgramadaNotification.PROGRAMADA_NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.cancel(pendingIntent)
    }
}