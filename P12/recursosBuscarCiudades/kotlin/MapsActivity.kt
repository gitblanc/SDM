package es.uniovi.sdm.buscarciudades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import es.uniovi.eii.sdm.buscarciudadc.data.GestorCiudades
import es.uniovi.eii.sdm.buscarciudadc.data.Resultados
import es.uniovi.sdm.buscarciudades.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private val DEV_CIUDADES= 5 // Ciudades devueltas en cada partida
    private lateinit var posCiudad: LatLng

    private val gc = GestorCiudades(DEV_CIUDADES)
    private val res= Resultados()

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
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val valdesSalas= LatLng(43.355115, -5.851297)
        mMap.addMarker(MarkerOptions().position(valdesSalas).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(valdesSalas))


    }

    private fun siguienteCiudad() {
        // pasamos a la siguiente ciudad y la ponemos en el campo
        try {
            val c= gc.nextCiudad()
            if (c!=null) {
                campoCiudad.text = c.nombre
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
	
	fun onClickAceptar(view: View) {}
    fun onClickSiguiente(view: View) {}


}