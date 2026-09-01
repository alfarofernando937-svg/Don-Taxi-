package com.don.taxi.app

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore

class UbicacionConductorActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    private val clienteUbicacion by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }

    private lateinit var estado: TextView
    private var compartiendo = false

    private val callback = object : LocationCallback() {

        override fun onLocationResult(resultado: LocationResult) {

            for (ubicacion in resultado.locations) {
                guardarUbicacion(ubicacion)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pantalla = LinearLayout(this)
        pantalla.orientation = LinearLayout.VERTICAL
        pantalla.setPadding(30, 30, 30, 30)

        estado = TextView(this)
        estado.text = "📍 Ubicación detenida"
        estado.textSize = 20f
        estado.setPadding(0, 0, 0, 30)

        val iniciar = Button(this)
        iniciar.text = "📍 COMPARTIR UBICACIÓN"

        val detener = Button(this)
        detener.text = "⛔ DETENER UBICACIÓN"

        val volver = Button(this)
        volver.text = "🔙 VOLVER"

        pantalla.addView(estado)
        pantalla.addView(iniciar)
        pantalla.addView(detener)
        pantalla.addView(volver)

        setContentView(pantalla)

        iniciar.setOnClickListener {
            iniciarUbicacion()
        }

        detener.setOnClickListener {
            detenerUbicacion()
        }

        volver.setOnClickListener {
            finish()
        }
    }

    private fun iniciarUbicacion() {

        if (
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                100
            )

            return
        }

        val solicitud = LocationRequest.Builder(
            5000
        )
            .setMinUpdateIntervalMillis(3000)
            .build()

        clienteUbicacion.requestLocationUpdates(
            solicitud,
            callback,
            mainLooper
        )

        compartiendo = true
        estado.text = "🟢 Compartiendo ubicación"
        mostrarMensaje("📍 Ubicación activada")
    }

    private fun detenerUbicacion() {

        clienteUbicacion.removeLocationUpdates(callback)

        compartiendo = false
        estado.text = "⛔ Ubicación detenida"

        mostrarMensaje("Ubicación detenida")
    }

    private fun guardarUbicacion(ubicacion: Location) {

        if (!compartiendo) {
            return
        }

        val datos = hashMapOf(
            "latitud" to ubicacion.latitude,
            "longitud" to ubicacion.longitude,
            "fecha" to System.currentTimeMillis()
        )

        db.collection("conductores")
            .document("conductor_actual")
            .set(datos)
            .addOnFailureListener {
                mostrarMensaje("❌ No se pudo guardar la ubicación")
            }
    }

    override fun onDestroy() {

        clienteUbicacion.removeLocationUpdates(callback)

        super.onDestroy()
    }

    private fun mostrarMensaje(mensaje: String) {

        Toast.makeText(
            this,
            mensaje,
            Toast.LENGTH_SHORT
        ).show()
    }
}