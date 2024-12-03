package com.sdm.buscarciudades

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.sdm.buscarciudades.databinding.ActivityMapsBinding
import es.uniovi.eii.sdm.buscarciudadc.data.GestorCiudades
import es.uniovi.eii.sdm.buscarciudadc.data.Resultados

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private val DEV_CIUDADES = 5 // Ciudades devueltas en cada partida
    private lateinit var posCiudad: LatLng

    private val gc = GestorCiudades(DEV_CIUDADES)
    private val res = Resultados()

    private var marcadorUsuario: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    // Todo lo relacionado con el mapa tiene que partir de aquí
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
        vistaInicial()
        siguienteCiudad()

        definirInteraccion()

        // Add a marker in Sydney and move the camera
//        val valdesSalas = LatLng(43.355115, -5.851297)
//        mMap.addMarker(MarkerOptions().position(valdesSalas).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(valdesSalas))
    }

    private fun definirInteraccion() {
        val controles = mMap.uiSettings
        controles.isZoomControlsEnabled = false
        controles.isZoomGesturesEnabled = false

        mMap.setOnMapLongClickListener { latlng ->
            if (marcadorUsuario != null)
                marcadorUsuario!!.remove()

            val marcadorOpciones = MarkerOptions()
                .position(latlng)
                .title("Marcador creado por el usuario")
            marcadorUsuario = mMap.addMarker(marcadorOpciones)
        }
    }

    private fun vistaInicial() {
        val ne = LatLng(35.56869922028171, -11.543628381652272)
        val so = LatLng(43.20171448909939, 6.16184434565758)
        val peninsula: LatLngBounds = LatLngBounds.builder().include(ne).include(so).build()
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(peninsula, 1080, 1080, 0))
    }

    private fun siguienteCiudad() {
        // pasamos a la siguiente ciudad y la ponemos en el campo
        try {
            val c = gc.nextCiudad()
            if (c != null) {
                binding.campoCiudad.text = c.nombre
                posCiudad = LatLng(c.latitud, c.longitud)
            }
        } catch (e: NoSuchElementException) {
            finalCiudades(res)
        }
    }

    private fun finalCiudades(res: Resultados) {
        // 1. Instancia un <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code>
        // con su constructor
        val builder = AlertDialog.Builder(this@MapsActivity)

        // 2. Encadena varios setters para establecer las
        // características del diálogo
        builder.setMessage("No hay más ciudades\nTu puntuación final es: ${res.puntos} puntos")//R.string.dialog_message)
            .setTitle("Fin del juego")//R.string.dialog_title)

        // 3. Obtiene la referencia a <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code>
        // desde <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
        val dialog = builder.create()
        dialog.show()

        // reinicio juego
        gc.reiniciarCiudades(DEV_CIUDADES)
        res.reiniciaPuntos()
    }

    fun onClickAceptar(view: View) {
        mMap.addMarker(
            MarkerOptions()
                .position(posCiudad)
                .title(binding.campoCiudad.text.toString())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.estrella32r))
        )

        for (i in 1 until 4) {
            mMap.addCircle(
                CircleOptions()
                    .center(posCiudad)
                    .radius(30000.0 * i)
                    .fillColor(0x1F00FF00)
                    .strokeColor(0xFFFFFFFF.toInt())
            )
        }

        // Dibujamos la línea
        mMap.addPolyline(
            PolylineOptions()
                .add(posCiudad, marcadorUsuario!!.position)
                .color(0xFFFF0000.toInt())
        )

        mMap.animateCamera((CameraUpdateFactory.newLatLngZoom(posCiudad, 8f)), 2000, null)

        res.addPuntos(calcularDistancia(posCiudad, marcadorUsuario!!.position))
    }

    private fun calcularDistancia(posCiudad: LatLng, position: LatLng): Float {
        val distancias = FloatArray(1)
        Location.distanceBetween(
            posCiudad.latitude, posCiudad.longitude,
            marcadorUsuario!!.position.latitude, marcadorUsuario!!.position.longitude,
            distancias
        )
        return distancias[0]
    }

    fun onClickSiguiente(view: View) {
        mMap.clear()
        vistaInicial()
        siguienteCiudad()
    }
}